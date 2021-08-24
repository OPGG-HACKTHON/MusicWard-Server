package io.github.opgg.music_ward_server.dto.track.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.opgg.music_ward_server.entity.playlist.Image;
import io.github.opgg.music_ward_server.entity.track.Track;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TrackMainResponse {

    private final Long id;
    private final String originalId;
    private final String title;
    private final String previewUrl;
    private final String artists;
    private final Image image;

    public TrackMainResponse(Track track) {
        this.id = track.getId();
        this.originalId = track.getOriginalId();
        this.title = track.getTitle();
        this.previewUrl = track.getPreviewUrl();
        this.artists = track.getArtists();
        this.image = track.getImage();
    }
}