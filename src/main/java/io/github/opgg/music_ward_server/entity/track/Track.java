package io.github.opgg.music_ward_server.entity.track;

import io.github.opgg.music_ward_server.entity.BaseEntity;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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

    private String title;

    private String songUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Builder
    public Track(String title, String songUrl, Playlist playlist) {
        this.title = title;
        this.songUrl = songUrl;
        this.playlist = playlist;
    }
}