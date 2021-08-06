package io.github.opgg.music_ward_server.entity.report;

import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    static User generateUser() {
        return User.builder()
                .email("test@email.com")
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
                .user(generateUser())
                .champion(generateChampion())
                .build();
    }

    @Test
    @DisplayName("Builder를 활용하여 Report 객체를 생성하는 테스트 - 성공")
    void createByBuilder() {

        // given
        User user = generateUser();
        Playlist playlist = generatePlaylist();

        // when
        Report report = Report.builder()
                .user(user)
                .playlist(playlist)
                .build();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(user, report.getUser()),
                () -> Assertions.assertEquals(playlist, report.getPlaylist())
        );
    }
}