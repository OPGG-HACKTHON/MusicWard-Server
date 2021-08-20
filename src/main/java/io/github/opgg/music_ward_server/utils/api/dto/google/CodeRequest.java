package io.github.opgg.music_ward_server.utils.api.dto.google;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeRequest {

    private String code;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantType;

}
