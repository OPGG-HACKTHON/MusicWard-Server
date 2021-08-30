package io.github.opgg.music_ward_server.service.user;

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
import io.github.opgg.music_ward_server.exception.EmailTooLongException;
import io.github.opgg.music_ward_server.exception.ExpiredRefreshTokenException;
import io.github.opgg.music_ward_server.exception.UserNotFoundException;
import io.github.opgg.music_ward_server.security.jwt.JwtTokenProvider;
import io.github.opgg.music_ward_server.utils.api.client.google.GoogleAuthClient;
import io.github.opgg.music_ward_server.utils.api.client.google.GoogleInfoClient;
import io.github.opgg.music_ward_server.utils.api.client.spotify.SpotifyAuthClient;
import io.github.opgg.music_ward_server.utils.api.client.spotify.SpotifyInfoClient;
import io.github.opgg.music_ward_server.utils.api.dto.google.*;
import io.github.opgg.music_ward_server.utils.api.dto.spotify.SpotifyAccessTokenResponse;
import io.github.opgg.music_ward_server.utils.api.dto.spotify.SpotifyTokenResponse;
import io.github.opgg.music_ward_server.utils.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String GOOGLE_LOGIN_LINK = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final Long GOOGLE_REFRESH_EXP = 604800L;
    private static final String SPOTIFY_LOGIN_LINK = "https://accounts.spotify.com/authorize";

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
    public TokenResponse getGoogleTokenByCode(String code) {
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
                                .build()
                );
            }
        } catch (RuntimeException e) {
            throw new EmailTooLongException();
        }

        Long userId = userRepository.findByGoogleEmail(userInfo.getEmail())
                .orElseThrow(UserNotFoundException::new).getId();

        return getToken(userId, response.getRefreshToken(), Type.GOOGLE, GOOGLE_REFRESH_EXP);
    }

    @Override
    public GoogleAccessTokenResponse getGoogleAccessToken(String refreshToken) {
        return googleAuthClient.getAccessTokenByRefreshToken(
                new GoogleAccessTokenRequest(googleProperties.getClientId(),
                        googleProperties.getClientSecret(), refreshToken, "refresh_token")
        );
    }

    @Override
    public TokenResponse getSpotifyTokenByCode(String code) {

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

        userRepository.findById(userId)
                .map(user -> userRepository.save(user.setSpotifyEmail(email)));

        return getToken(userId, response.getRefreshToken(), Type.SPOTIFY, -1L);
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

        user.getComments().forEach(
                comment -> comment.changeUser(ghostUser)
        );

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
        tokenRepository.findById(userId + type.name())
                .or(() -> Optional.of(new Token(userId + type.name(),
                        oauthToken, refreshExp)))
                .ifPresent(token -> tokenRepository.save(token.update(oauthToken, oauthExp)));

        return new TokenResponse(accessToken, refreshToken, oauthToken, type.name());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

}