package io.github.opgg.music_ward_server.entity.track;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.opgg.music_ward_server.entity.BaseEntity;
import io.github.opgg.music_ward_server.entity.playlist.Image;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_track")
public class Track extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private Long id;

    @Column(nullable = false)
    private String originalId;

    @Column(nullable = false)
    private String title;

    private String previewUrl;

    @Column(nullable = false)
    private String artists;

    @Embedded
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    @Builder
    public Track(String originalId, String title, String previewUrl, String artists, Image image, Playlist playlist) {
        this.originalId = originalId;
        this.title = title;
        this.previewUrl = previewUrl;
        this.artists = artists;
        this.image = image;
        this.playlist = playlist;
    }
}