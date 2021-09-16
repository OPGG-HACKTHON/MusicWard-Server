package io.github.opgg.music_ward_server.entity.tag;

import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.playlist.Image;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.Provider;
import io.github.opgg.music_ward_server.entity.user.Role;
import io.github.opgg.music_ward_server.entity.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    static User generateUser(String email) {
        return User.builder()
                .googleEmail(email)
                .spotifyEmail(email)
                .name("test")
                .role(Role.ROLE_ADMIN)
                .nickname("hideonbush")
                .build();
    }

    static Champion generateChampion() {
        return Champion.builder()
                .name("가렌")
                .title("데마시아의 힘")
                .englishName("Garen")
                .story("가렌은 불굴의 선봉대를 이끄는 고결하고 자긍심 강한 군인이다.")
                .position("전사")
                .profileImageUrl("/images/profile/garen.jpg")
                .imageUrl("/images/garen.jpg")
                .build();
    }

    static Playlist generatePlaylist(User user) {
        return Playlist.builder()
                .originalId("1234")
                .provider(Provider.YOUTUBE)
                .title("테스트 플레이 리스트")
                .description("테스트 플레이 리스트 설명")
                .image(new Image("url", "640", "640"))
                .externalUrl("외부 url")
                .user(user)
                .champion(generateChampion())
                .build();
    }

    @Test
    @DisplayName("Builder를 활용하여 Tag 객체를 생성하는 테스트 - 성공")
    void createByBuilder() {

        // given
        String title = "테스트 태그명";
        User user = generateUser("test@email.com");
        Playlist playlist = generatePlaylist(user);

        // when
        Tag tag = Tag.builder()
                .title(title)
                .playlist(playlist)
                .build();

        // then
        assertAll(
                () -> assertEquals(title, tag.getTitle()),
                () -> assertEquals(playlist, tag.getPlaylist())
        );
    }
}