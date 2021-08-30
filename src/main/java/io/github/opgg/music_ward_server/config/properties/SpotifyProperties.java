package io.github.opgg.music_ward_server.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("oauth.spotify")
public class SpotifyProperties {

    private String clientId;
    private String clientSecret;
    private String redirectUri;

}
