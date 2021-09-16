package io.github.opgg.music_ward_server.service.user;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import io.github.opgg.music_ward_server.config.properties.GoogleProperties;
import io.github.opgg.music_ward_server.config.properties.SpotifyProperties;
import io.github.opgg.music_ward_server.dto.user.request.ModifyNicknameRequest;
import io.github.opgg.music_ward_server.dto.user.request.RefreshTokenRequest;
import io.github.opgg.music_ward_server.dto.user.response.LinkResponse;
import io.github.opgg.music_ward_server.dto.user.response.TokenResponse;
import io.github.opgg.music_ward_server.dto.user.response.UserInfoResponse;
import io.github.opgg.music_ward_server.entity.report.ReportRepository;
import io.github.opgg.music_ward_server.entity.token.Token;
import io.github.opgg.music_ward_server.entity.token.TokenRepository;
import io.github.opgg.music_ward_server.entity.token.Type;
import io.github.opgg.music_ward_server.entity.user.Role;
import io.github.opgg.music_ward_server.entity.user.User;
import io.github.opgg.music_ward_server.entity.user.UserRepository;
import io.github.opgg.music_ward_server.entity.ward.WardRepository;
import io.github.opgg.music_ward_server.exception.AlreadyRegisteredEmailException;
import io.github.opgg.music_ward_server.exception.EmailTooLongException;
import io.github.opgg.music_ward_server.exception.ExpiredRefreshTokenException;
import io.github.opgg.music_ward_server.exception.UserNotFoundException;
import io.github.opgg.music_ward_server.security.jwt.JwtTokenProvider;
import io.github.opgg.music_ward_server.utils.api.client.google.GoogleAuthClient;
import io.github.opgg.music_ward_server.utils.api.client.google.GoogleInfoClient;
import io.github.opgg.music_ward_server.utils.api.client.spotify.SpotifyAuthClient;
import io.github.opgg.music_ward_server.utils.api.client.spotify.SpotifyInfoClient;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleAccessTokenRequest;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleAccessTokenResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleCodeRequest;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleInfoResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleTokenResponse;
import io.github.opgg.music_ward_server.utils.api.dto.spotify.SpotifyAccessTokenResponse;
import io.github.opgg.music_ward_server.utils.api.dto.spotify.SpotifyTokenResponse;
import io.github.opgg.music_ward_server.utils.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String GOOGLE_LOGIN_LINK = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final Long REFRESH_EXP = -1L;
    private static final String SPOTIFY_LOGIN_LINK = "https://accounts.spotify.com/authorize";
    private static final Random random = new Random();

    private final GoogleProperties googleProperties;
    private final SpotifyProperties spotifyProperties;

    @Value("${jwt.refresh.exp}")
    private Long refreshExp;

    private final GoogleAuthClient googleAuthClient;
    private final GoogleInfoClient googleInfoClient;
    private final SpotifyAuthClient spotifyAuthClient;
    private final SpotifyInfoClient spotifyInfoClient;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final TokenRepository tokenRepository;
    private final WardRepository wardRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LinkResponse getGoogleLink() {
        return new LinkResponse(GOOGLE_LOGIN_LINK +
                "?client_id=" + googleProperties.getClientId() +
                "&scope=https://www.googleapis.com/auth/youtube%20" +
                "https://www.googleapis.com/auth/userinfo.email%20" +
                "https://www.googleapis.com/auth/userinfo.profile" +
                "&response_type=code" +
                "&access_type=offline" +
                "&redirect_uri=" + URLEncoder.encode(googleProperties.getRedirectUri(), StandardCharsets.UTF_8));
    }

    @Override
    public LinkResponse getSpotifyLink() {
        return new LinkResponse(SPOTIFY_LOGIN_LINK +
                "?client_id=" + spotifyProperties.getClientId() +
                "&scope=" + URLEncoder.encode("user-read-email playlist-read-private", StandardCharsets.UTF_8) +
                "&response_type=code" +
                "&redirect_uri=" + URLEncoder.encode(spotifyProperties.getRedirectUri(), StandardCharsets.UTF_8));
    }

    @Override
    public TokenResponse getTokenByGoogleCode(String code) {
        GoogleTokenResponse response = googleAuthClient.getTokenByCode(
                new GoogleCodeRequest(URLDecoder.decode(code, StandardCharsets.UTF_8),
                        googleProperties.getClientId(), googleProperties.getClientSecret(),
                        googleProperties.getRedirectUri(), "authorization_code")
        );

        GoogleInfoResponse userInfo
                = googleInfoClient.getEmail("Bearer" + response.getAccessToken());

        try{
            if(userRepository.findByGoogleEmail(userInfo.getEmail()).isEmpty()) {
                userRepository.save(
                        User.builder()
                                .googleEmail(userInfo.getEmail())
                                .name(userInfo.getName())
                                .role(Role.ROLE_USER)
                                .nickname(getNickname())
                                .build()
                );
            }
        } catch (RuntimeException e) {
            throw new EmailTooLongException();
        }

        Long userId = userRepository.findByGoogleEmail(userInfo.getEmail())
                .orElseThrow(UserNotFoundException::new).getId();

        return getToken(userId, response.getRefreshToken(), Type.GOOGLE, REFRESH_EXP);
    }

    @Override
    public GoogleAccessTokenResponse getGoogleAccessToken(String refreshToken) {
        return googleAuthClient.getAccessTokenByRefreshToken(
                new GoogleAccessTokenRequest(googleProperties.getClientId(),
                        googleProperties.getClientSecret(), refreshToken, "refresh_token")
        );
    }

    @Override
    public TokenResponse getTokenBySpotifyCodeWithJwt(String code) {

        Long userId = SecurityUtil.getCurrentUserId();

        SpotifyTokenResponse response =
                spotifyAuthClient.getTokenByCode("authorization_code",
                URLDecoder.decode(code, StandardCharsets.UTF_8),
                spotifyProperties.getRedirectUri(), "Basic " +
                Base64.encodeBase64String((spotifyProperties.getClientId() +
                        ":" + spotifyProperties.getClientSecret()).getBytes())
        );

        String email = spotifyInfoClient.getEmail("Bearer " + response.getAccessToken())
                .getEmail();

		try {
			userRepository.findById(userId)
					.map(user -> userRepository.save(user.setSpotifyEmail(email)));
		} catch(RuntimeException e) {
			throw new AlreadyRegisteredEmailException();
		}

        return getToken(userId, response.getRefreshToken(), Type.SPOTIFY, REFRESH_EXP);
    }

    @Override
    public TokenResponse getTokenBySpotifyCode(String code) {

        SpotifyTokenResponse response =
                spotifyAuthClient.getTokenByCode("authorization_code",
                        URLDecoder.decode(code, StandardCharsets.UTF_8),
                        spotifyProperties.getRedirectUri(), "Basic " +
                                Base64.encodeBase64String((spotifyProperties.getClientId() +
                                        ":" + spotifyProperties.getClientSecret()).getBytes())
                );

        String email = spotifyInfoClient.getEmail("Bearer " + response.getAccessToken())
                .getEmail();

        Long userId = userRepository.findBySpotifyEmail(email)
                .orElseThrow(UserNotFoundException::new).getId();

        return getToken(userId, response.getRefreshToken(), Type.SPOTIFY, refreshExp);
    }

    @Override
    public SpotifyAccessTokenResponse getSpotifyAccessToken(String refreshToken) {

        String authorization = "Basic " +
                Base64.encodeBase64String((spotifyProperties.getClientId() +
                        ":" + spotifyProperties.getClientSecret()).getBytes());

        return spotifyAuthClient.getAccessTokenByRefreshToken(authorization, "refresh_token", refreshToken);
    }

    @Override
    public UserInfoResponse getUserInfo() {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = getUser(userId);

        return new UserInfoResponse(
                user.getGoogleEmail(), user.getSpotifyEmail(),
                user.getName(), user.getNickname()
        );
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        Integer userId = Integer.valueOf(
                jwtTokenProvider.parseRefreshToken(request.getRefreshToken()));

        tokenRepository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(ExpiredRefreshTokenException::new);

        String accessToken = jwtTokenProvider
                .generateAccessToken(userId);
        String refreshToken = jwtTokenProvider
                .generateRefreshToken(userId);

        tokenRepository.findById(userId + Type.MUSICWARD.name())
                .ifPresent(token -> tokenRepository.save(token.update(refreshToken, refreshExp)));

        return new TokenResponse(accessToken, refreshToken,
                null, Type.MUSICWARD.name());
    }

    @Override
    @Transactional
    public void withdrawalUser() {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = getUser(userId);

        User ghostUser = getUser(0L);

		if(user.getComments() != null)
			user.getComments().forEach(
					comment -> comment.changeUser(ghostUser)
			);

		if(user.getPlaylists() != null)
			user.getPlaylists().forEach(
					playlist -> playlist.changeUser(ghostUser)
			);

        wardRepository.deleteByUser(user);

        reportRepository.deleteByUser(user);

        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void modifyNickname(ModifyNicknameRequest request) {
        User user = getUser(SecurityUtil.getCurrentUserId());

        user.setNickname(request.getNickname());
    }

    private TokenResponse getToken(Long userId, String oauthToken, Type type, Long oauthExp) {
        String accessToken = jwtTokenProvider.generateAccessToken(Math.toIntExact(userId));
        String refreshToken = jwtTokenProvider.generateRefreshToken(Math.toIntExact(userId));

        tokenRepository.findById(userId + Type.MUSICWARD.name())
                .or(() -> Optional.of(new Token(userId + Type.MUSICWARD.name(), refreshToken, refreshExp)))
                .ifPresent(token -> tokenRepository.save(token.update(refreshToken, refreshExp)));
		if(tokenRepository.findById(userId + type.name()).isEmpty()) {
			tokenRepository.save(new Token(userId + type.name(),
					oauthToken, oauthExp));
		}

        String oauthRefreshToken = tokenRepository.findById(userId + type.name())
				.map(Token::getRefreshToken)
				.orElse(null);

        return new TokenResponse(accessToken, refreshToken, oauthRefreshToken, type.name());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private String getNickname() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return "가렌-" + stringBuilder.toString();
    }

}