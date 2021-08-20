package io.github.opgg.music_ward_server.utils.api.dto.google;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GoogleTokenResponse {

    private String accessToken;
    private String refreshToken;

}
