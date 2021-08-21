package io.github.opgg.music_ward_server.entity.track;

import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.Provider;
import io.github.opgg.music_ward_server.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TrackTest {

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
                .title("테스트 플레이 리스트")
                .thumbnailImageUrl("/images/thumbnail/test.png")
                .serviceType(Provider.YOUTUBE)
                .user(generateUser())
                .champion(generateChampion())
                .build();
    }

    @Test
    @DisplayName("Builder를 활용하여 Song 객체를 생성하는 테스트 - 성공")
    void createByBuilder() {

        // given
        String title = "테스트 노래";
        String songUrl = "/song/test.mp3";
        Playlist playlist = generatePlaylist();

        // when
        Track song = Track.builder()
                .title(title)
                .songUrl(songUrl)
                .playlist(playlist)
                .build();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(title, song.getTitle()),
                () -> Assertions.assertEquals(songUrl, song.getSongUrl()),
                () -> Assertions.assertEquals(playlist, song.getPlaylist())
        );
    }
}