package io.github.opgg.music_ward_server.service.user;

import io.github.opgg.music_ward_server.dto.user.response.LinkResponse;
import io.github.opgg.music_ward_server.dto.user.response.TokenResponse;
import io.github.opgg.music_ward_server.entity.token.Token;
import io.github.opgg.music_ward_server.entity.token.TokenRepository;
import io.github.opgg.music_ward_server.entity.token.Type;
import io.github.opgg.music_ward_server.entity.user.Role;
import io.github.opgg.music_ward_server.entity.user.User;
import io.github.opgg.music_ward_server.entity.user.UserRepository;
import io.github.opgg.music_ward_server.exception.EmailTooLongException;
import io.github.opgg.music_ward_server.exception.UserNotFoundException;
import io.github.opgg.music_ward_server.security.jwt.JwtTokenProvider;
import io.github.opgg.music_ward_server.utils.api.client.GoogleAuthClient;
import io.github.opgg.music_ward_server.utils.api.client.GoogleInfoClient;
import io.github.opgg.music_ward_server.utils.api.dto.google.CodeRequest;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleAccessTokenRequest;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleAccessTokenResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String GOOGLE_LOGIN_LINK = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final Long GOOGLE_REFRESH_EXP = 604800L;
    private static final String SPOTIFY_LOGIN_LINK = "https://accounts.spotify.com/authorize";

    @Value("${oauth.google.client_id}")
    private String googleClientId;

    @Value("${oauth.google.client_secret}")
    private String googleClientSecret;

    @Value("${oauth.google.redirect_uri}")
    private String googleRedirectUri;

    @Value("${oauth.spotify.client_id}")
    private String spotifyClientId;

    @Value("${oauth.spotify.client_secret}")
    private String spotifyClientSecret;

    @Value("${oauth.spotify.redirect_uri}")
    private String spotifyRedirectUri;

    @Value("${jwt.refresh.exp}")
    private Long refreshExp;

    private final GoogleAuthClient googleAuthClient;
    private final GoogleInfoClient googleInfoClient;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LinkResponse getGoogleLink() {
        return new LinkResponse(GOOGLE_LOGIN_LINK +
                "?client_id=" + googleClientId +
                "&scope=https://www.googleapis.com/auth/youtube%20https://www.googleapis.com/auth/userinfo.email" +
                "&response_type=code" +
                "&access_type=offline" +
                "&redirect_uri=" + URLEncoder.encode(googleRedirectUri, StandardCharsets.UTF_8));
    }

    @Override
    public LinkResponse getSpotifyLink() {
        return new LinkResponse(SPOTIFY_LOGIN_LINK +
                "?client_id=" + spotifyClientId +
                "&scope=" + URLEncoder.encode("user-read-email playlist-read-private", StandardCharsets.UTF_8) +
                "&response_type=code" +
                "&redirect_uri=" + URLEncoder.encode(spotifyRedirectUri, StandardCharsets.UTF_8));
    }

    @Override
    public TokenResponse getTokenByCode(String code) {
        GoogleTokenResponse response = googleAuthClient.getTokenByCode(
                new CodeRequest(code, googleClientId, googleClientSecret, googleRedirectUri, "authorization_code")
        );

        String email = googleInfoClient.getEmail("Bearer" + response.getAccessToken()).getEmail();

        try{
            if(userRepository.findByGoogleEmail(email).isEmpty()) {
                userRepository.save(
                        User.builder()
                                .googleEmail(email)
                                .role(Role.ROLE_USER)
                                .build()
                );
            }
        } catch (RuntimeException e) {
            throw new EmailTooLongException();
        }

        Long userId = userRepository.findByGoogleEmail(email)
                .orElseThrow(UserNotFoundException::new).getId();

        String accessToken = jwtTokenProvider.generateAccessToken(Math.toIntExact(userId));
        String refreshToken = jwtTokenProvider.generateRefreshToken(Math.toIntExact(userId));

        tokenRepository.findById(userId + Type.MUSICWARD.name())
                .or(() -> Optional.of(new Token(userId + Type.MUSICWARD.name(), refreshToken, refreshExp)))
                .ifPresent(token -> tokenRepository.save(token.update(refreshToken, refreshExp)));
        tokenRepository.findById(userId + Type.GOOGLE.name())
                .or(() -> Optional.of(new Token(userId + Type.GOOGLE.name(),
                        response.getRefreshToken(), GOOGLE_REFRESH_EXP)))
                .ifPresent(token -> tokenRepository.save(token.update(response.getRefreshToken(), GOOGLE_REFRESH_EXP)));

        return new TokenResponse(accessToken, refreshToken, response.getRefreshToken(), Type.GOOGLE.name());
    }

    @Override
    public GoogleAccessTokenResponse getAccessToken(String refreshToken) {
        return googleAuthClient.getAccessTokenByRefreshToken(
                new GoogleAccessTokenRequest(googleClientId, googleClientSecret, refreshToken, "refresh_token")
        );
    }

}