package io.github.opgg.music_ward_server.dto.user.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.opgg.music_ward_server.entity.user.User;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserMainResponse {

    private final Long userId;
    private final String googleEmail;
    private final String spotifyEmail;
    private final String name;
    private final String nickname;

    public UserMainResponse(User user) {
        this.userId = user.getId();
        this.googleEmail = user.getGoogleEmail();
        this.spotifyEmail = user.getSpotifyEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
    }
}