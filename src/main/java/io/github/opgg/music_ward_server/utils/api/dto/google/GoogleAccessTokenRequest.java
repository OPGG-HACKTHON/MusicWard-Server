package io.github.opgg.music_ward_server.utils.api.dto.google;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleAccessTokenRequest {

    private String client_id;
    private String client_secret;
    private String refresh_token;
    private String grant_type;
}