package io.github.opgg.music_ward_server.service.user;

import io.github.opgg.music_ward_server.dto.user.response.GoogleLinkResponse;
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
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String GOOGLE_LOGIN_LINK = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final Long GOOGLE_REFRESH_EXP = 604800L;

    @Value("${oauth.google.client_id}")
    private String clientId;

    @Value("${oauth.google.client_secret}")
    private String clientSecret;

    @Value("${oauth.redirect_uri}")
    private String redirectUri;

    @Value("${jwt.refresh.exp}")
    private Long refreshExp;

    private final GoogleAuthClient googleAuthClient;
    private final GoogleInfoClient googleInfoClient;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public GoogleLinkResponse getGoogleLink() {
        return new GoogleLinkResponse(GOOGLE_LOGIN_LINK +
                "?client_id=" + clientId +
                "&scope=https%3A//www.googleapis.com/auth/userinfo.email" +
                "&response_type=code" +
                "&access_type=offline" +
                "&redirect_uri=" + redirectUri);
    }

    @Override
    public TokenResponse getTokenByCode(String code) {
        GoogleTokenResponse response = googleAuthClient.getTokenByCode(
                new CodeRequest(code, clientId, clientSecret, redirectUri, "authorization_code")
        );

        String email = googleInfoClient.getEmail(response.getAccessToken()).getEmail();

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

        tokenRepository.findByIdAndType(userId, Type.MUSICWARD)
                .or(() -> Optional.of(new Token(userId, Type.MUSICWARD, refreshToken, refreshExp)))
                .ifPresent(token -> tokenRepository.save(token.update(refreshToken, refreshExp)));
        tokenRepository.findByIdAndType(userId, Type.GOOGLE)
                .or(() -> Optional.of(new Token(userId, Type.GOOGLE,
                        response.getRefreshToken(), GOOGLE_REFRESH_EXP)))
                .ifPresent(token -> tokenRepository.save(token.update(refreshToken, GOOGLE_REFRESH_EXP)));

        return new TokenResponse(accessToken, refreshToken,
                response.getRefreshToken(), Type.GOOGLE.name());
    }

}
