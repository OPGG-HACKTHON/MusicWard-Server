package io.github.opgg.music_ward_server.utils.api.dto.spotify;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyAccessTokenResponse {

    private String accessToken;
    private String tokenType;

    public String getAccessTokenAndTokenType() {
        return tokenType + " " + accessToken;
    }
}