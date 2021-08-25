package io.github.opgg.music_ward_server.service.user;

import io.github.opgg.music_ward_server.dto.user.request.RefreshTokenRequest;
import io.github.opgg.music_ward_server.dto.user.response.LinkResponse;
import io.github.opgg.music_ward_server.dto.user.response.TokenResponse;
import io.github.opgg.music_ward_server.dto.user.response.UserInfoResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleAccessTokenResponse;

public interface UserService {
    LinkResponse getGoogleLink();
    LinkResponse getSpotifyLink();
    TokenResponse getGoogleTokenByCode(String code);
    GoogleAccessTokenResponse getGoogleAccessToken(String refreshToken);
    TokenResponse getSpotifyTokenByCode(String code);
    UserInfoResponse getUserInfo();
    TokenResponse refreshToken(RefreshTokenRequest request);
}