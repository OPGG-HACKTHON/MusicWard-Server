package io.github.opgg.music_ward_server.utils.api.dto.google;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleAccessTokenRequest {

    private String clientId;
    private String clientSecret;
    private String refreshToken;
    private String grantType;
}