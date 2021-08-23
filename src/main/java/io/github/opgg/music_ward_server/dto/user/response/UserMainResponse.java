package io.github.opgg.music_ward_server.dto.user.response;

import io.github.opgg.music_ward_server.entity.user.User;
import lombok.Getter;

@Getter
public class UserMainResponse {

    private final Long userId;
    private final String googleEmail;
    private final String spotifyEmail;
    private final String name;
    private final String nickName;

    public UserMainResponse(User user) {
        this.userId = user.getId();
        this.googleEmail = user.getGoogleEmail();
        this.spotifyEmail = user.getSpotifyEmail();
        this.name = user.getName();
        this.nickName = user.getNickName();
    }
}