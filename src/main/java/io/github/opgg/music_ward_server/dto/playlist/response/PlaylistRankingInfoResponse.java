package io.github.opgg.music_ward_server.dto.playlist.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.opgg.music_ward_server.entity.playlist.Image;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlaylistRankingInfoResponse {

    private Long playlistId;
    private String title;
    private String description;
    private Image image;

    public PlaylistRankingInfoResponse(Playlist playlist) {
        this.playlistId = playlist.getId();
        this.title = playlist.getTitle();
        this.description = playlist.getDescription();
        this.image = playlist.getImage();
    }
}