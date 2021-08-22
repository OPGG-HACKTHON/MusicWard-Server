package io.github.opgg.music_ward_server.entity.comment;

import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.playlist.Image;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.Provider;
import io.github.opgg.music_ward_server.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {

    static User generateUser() {
        return User.builder()
                .googleEmail("test@email.com")
                .build();
    }

    static Champion generateChampion() {
        return Champion.builder()
                .name("가렌")
                .englishName("Garen")
                .story("가렌은 불굴의 선봉대를 이끄는 고결하고 자긍심 강한 군인이다.")
                .position("전사")
                .profileImageUrl("/images/profile/garen.jpg")
                .imageUrl("/images/garen.jpg")
                .build();
    }

    static Playlist generatePlaylist() {
        return Playlist.builder()
                .originalId("1234")
                .provider(Provider.YOUTUBE)
                .title("테스트 플레이 리스트")
                .description("테스트 플레이 리스트 설명")
                .image(new Image("url", "640", "640"))
                .externalUrl("외부 url")
                .user(generateUser())
                .champion(generateChampion())
                .build();
    }

    @Test
    @DisplayName("Builder를 활용하여 Comment 객체를 생성하는 테스트 - 성공")
    void createByBuilder() {

        // given
        String content = "테스트 댓글";
        User user = generateUser();
        Playlist playlist = generatePlaylist();

        // when
        Comment comment = Comment.builder()
                .content(content)
                .user(user)
                .playlist(playlist)
                .build();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(content, comment.getContent()),
                () -> Assertions.assertEquals(user, comment.getUser()),
                () -> Assertions.assertEquals(playlist, comment.getPlaylist())
        );
    }
}