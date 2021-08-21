package io.github.opgg.music_ward_server.entity.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@AllArgsConstructor
@RedisHash
public class Token {

    @Id
    private final Long id;

    private String musicWardRefreshToken;

    private String googleRefreshToken;

    private String spotifyRefreshToken;

    @TimeToLive
    private Long ttl;

    public Token update(String musicWardRefreshToken, String googleRefreshToken, String spotifyRefreshToken, Long ttl) {
        this.musicWardRefreshToken = musicWardRefreshToken;
        this.googleRefreshToken = googleRefreshToken;
        this.spotifyRefreshToken = spotifyRefreshToken;
        this.ttl = ttl;
        return this;
    }
}
