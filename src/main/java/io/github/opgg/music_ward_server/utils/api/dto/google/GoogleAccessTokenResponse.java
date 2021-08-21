package io.github.opgg.music_ward_server.utils.api.dto.google;

import lombok.Getter;

@Getter
public class GoogleAccessTokenResponse {

    private String access_token;
    private String token_type;

    public String getAccessTokenAndTokenType() {
        return token_type + " " + access_token;
    }
}