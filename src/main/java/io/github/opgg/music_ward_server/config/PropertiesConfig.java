package io.github.opgg.music_ward_server.config;

import io.github.opgg.music_ward_server.config.properties.GoogleProperties;
import io.github.opgg.music_ward_server.config.properties.SpotifyProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({GoogleProperties.class, SpotifyProperties.class})
public class PropertiesConfig {
}
