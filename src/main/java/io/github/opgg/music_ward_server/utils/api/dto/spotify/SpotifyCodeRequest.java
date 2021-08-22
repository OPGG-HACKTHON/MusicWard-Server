package io.github.opgg.music_ward_server.utils.api.dto.spotify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyCodeRequest {

    private String code;
    private String redirectUri;
    private String grantType;

}
