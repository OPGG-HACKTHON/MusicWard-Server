package io.github.opgg.music_ward_server.dto.track.request;

import io.github.opgg.music_ward_server.entity.playlist.Image;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.track.Track;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TrackSaveRequest {

    private String originalId;
    private String title;
    private String previewUrl;
    private Image image;
    private String artists;

    @Builder
    public TrackSaveRequest(String originalId, String title, String previewUrl, Image image, String artists) {
        this.originalId = originalId;
        this.title = title;
        this.previewUrl = previewUrl;
        this.image = image;
        this.artists = artists;
    }

    public Track toEntity(Playlist playlist) {
        return Track.builder()
                .originalId(originalId)
                .title(title)
                .previewUrl(previewUrl)
                .image(image)
                .artists(artists)
                .playlist(playlist)
                .build();
    }
}