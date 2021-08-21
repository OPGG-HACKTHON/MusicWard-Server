package io.github.opgg.music_ward_server.utils.api.dto.google;

import lombok.Getter;

@Getter
public class GoogleAccessTokenResponse {

    private String accessToken;
    private String tokenType;

    public String getAccessTokenAndTokenType() {
        return tokenType + " " + accessToken;
    }
}