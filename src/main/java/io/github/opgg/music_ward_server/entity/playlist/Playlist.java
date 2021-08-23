package io.github.opgg.music_ward_server.entity.playlist;

import io.github.opgg.music_ward_server.entity.BaseEntity;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_playlist")
public class Playlist extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long id;

    @Column(nullable = false)
    private String originalId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 200)
    private String description;

    @Embedded
    private Image image;

    @Column(nullable = false)
    private String externalUrl;

    private Integer view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "champion_id")
    private Champion champion;

    @Builder
    public Playlist(String originalId, Provider provider, String title, String description,
                    Image image, String externalUrl, User user, Champion champion) {
        this.originalId = originalId;
        this.provider = provider;
        this.title = title;
        this.description = description;
        this.image = image;
        this.externalUrl = externalUrl;
        this.view = 0;
        this.user = user;
        this.champion = champion;
    }

    public void addView() {
        this.view++;
    }

    public void update(Playlist playlist) {
        this.title = playlist.getTitle();
        this.description = playlist.getDescription();
        this.champion = playlist.getChampion();
    }
}