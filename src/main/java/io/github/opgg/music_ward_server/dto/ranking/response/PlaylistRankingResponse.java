package io.github.opgg.music_ward_server.dto.ranking.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.opgg.music_ward_server.dto.playlist.response.PlaylistRankingInfoResponse;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlaylistRankingResponse {

    private PlaylistRankingInfoResponse playlist;
    private int view;
    private int wardsTotal;
    private int commentsTotal;
    private String createdDate;
    private String lastModifiedDate;

    public PlaylistRankingResponse(Playlist playlist) {
        this.playlist = new PlaylistRankingInfoResponse(playlist);
        this.view = playlist.getView();
        this.wardsTotal = playlist.getWards().size();
        this.commentsTotal = playlist.getComments().size();
        this.createdDate = playlist.getCreatedDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.lastModifiedDate = playlist.getLastModifiedDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}