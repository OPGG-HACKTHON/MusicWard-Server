package io.github.opgg.music_ward_server.utils.api.dto.google;

import lombok.Getter;

@Getter
public class GoogleTokenResponse {

    private String access_token;
    private String refresh_token;
    private String token_type;

    public String getAccessToken() {
        return access_token;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public String getTokenType() {
        return token_type;
    }

    public String getAccessTokenAndTokenType() {
        return token_type + " " + access_token;
    }
}