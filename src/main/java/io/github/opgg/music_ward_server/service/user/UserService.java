package io.github.opgg.music_ward_server.service.user;

import io.github.opgg.music_ward_server.dto.user.response.LinkResponse;
import io.github.opgg.music_ward_server.dto.user.response.TokenResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleAccessTokenResponse;

public interface UserService {
    LinkResponse getGoogleLink();
    LinkResponse getSpotifyLink();
    TokenResponse getTokenByCode(String code);
    GoogleAccessTokenResponse getAccessToken(String refreshToken);
}