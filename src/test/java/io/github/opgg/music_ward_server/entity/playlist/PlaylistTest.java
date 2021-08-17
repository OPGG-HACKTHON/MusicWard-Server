package io.github.opgg.music_ward_server.entity.playlist;

import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlaylistTest {

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

    @Test
    @DisplayName("Builder를 활용하여 Playlist 객체를 생성하는 테스트 - 성공")
    void createByBuilder() {

        // given
        String title = "테스트 플레이 리스트";
        String thumbnailImageUrl = "/images/thumbnail/test.png";
        User user = generateUser();
        Champion champion = generateChampion();

        // when
        Playlist playlist = Playlist.builder()
                .title(title)
                .thumbnailImageUrl(thumbnailImageUrl)
                .user(user)
                .champion(champion)
                .build();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(title, playlist.getTitle()),
                () -> Assertions.assertEquals(thumbnailImageUrl, playlist.getThumbnailImageUrl()),
                () -> Assertions.assertEquals(0, playlist.getView()),
                () -> Assertions.assertEquals(user, playlist.getUser()),
                () -> Assertions.assertEquals(champion, playlist.getChampion())
        );
    }

    @Test
    @DisplayName("playlist의 조회수를 1 증가시킨다. - 성공")
    void addView() {

        // given
        String title = "테스트 플레이 리스트";
        String thumbnailImageUrl = "/images/thumbnail/test.png";
        ServiceType serviceType = ServiceType.YOUTUBE;
        User user = generateUser();
        Champion champion = generateChampion();

        Playlist playlist = Playlist.builder()
                .title(title)
                .thumbnailImageUrl(thumbnailImageUrl)
                .serviceType(serviceType)
                .user(user)
                .champion(champion)
                .build();

        // when
        playlist.addView();

        // then
        Assertions.assertEquals(1, playlist.getView());
    }
}