package io.github.opgg.music_ward_server.service.user;

import io.github.opgg.music_ward_server.dto.user.request.ModifyNicknameRequest;
import io.github.opgg.music_ward_server.dto.user.request.RefreshTokenRequest;
import io.github.opgg.music_ward_server.dto.user.response.LinkResponse;
import io.github.opgg.music_ward_server.dto.user.response.TokenResponse;
import io.github.opgg.music_ward_server.dto.user.response.UserInfoResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleAccessTokenResponse;
import io.github.opgg.music_ward_server.utils.api.dto.spotify.SpotifyAccessTokenResponse;

public interface UserService {
    LinkResponse getGoogleLink();
    LinkResponse getSpotifyLink();
    TokenResponse getTokenByGoogleCode(String code);
    GoogleAccessTokenResponse getGoogleAccessToken(String refreshToken);
    TokenResponse getTokenBySpotifyCodeWithJwt(String code);
    TokenResponse getTokenBySpotifyCode(String code);
    SpotifyAccessTokenResponse getSpotifyAccessToken(String refreshToken);
    UserInfoResponse getUserInfo();
    TokenResponse refreshToken(RefreshTokenRequest request);
    void withdrawalUser();
    void modifyNickname(ModifyNicknameRequest request);
}