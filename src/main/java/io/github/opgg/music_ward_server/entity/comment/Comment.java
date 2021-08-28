package io.github.opgg.music_ward_server.entity.comment;

import io.github.opgg.music_ward_server.entity.BaseEntity;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.user.User;
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
@Entity(name = "tbl_comment")
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Builder
    public Comment(String content, User user, Playlist playlist) {
        this.content = content;
        this.user = user;
        this.playlist = playlist;
        this.playlist.getComments().add(this);
    }

    public void changeUser(User user) {
        if(this.user != null) {
            this.user.getComments().remove(this);
        }
        this.user = user;
        user.getComments().add(this);
    }

    public void editContent(String content) {
        this.content = content;
    }

}