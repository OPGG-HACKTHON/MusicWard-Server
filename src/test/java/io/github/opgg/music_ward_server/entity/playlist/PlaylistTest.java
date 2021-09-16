package io.github.opgg.music_ward_server.entity.playlist;

import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.user.Role;
import io.github.opgg.music_ward_server.entity.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {

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

    @Test
    @DisplayName("Builder를 활용하여 Playlist 객체를 생성하는 테스트 - 성공")
    void createByBuilder() {

        // given
        String originalId = "1234";
        Provider provider = Provider.YOUTUBE;
        String title = "테스트 플레이 리스트";
        String description = "테스트 설명";
        Image image = new Image("url", "640", "640");
        String externalUrl = "외부 url";
        User user = generateUser("test@email.com");
        Champion champion = generateChampion();

        // when
        Playlist playlist = Playlist.builder()
                .originalId(originalId)
                .provider(provider)
                .title(title)
                .description(description)
                .image(image)
                .externalUrl(externalUrl)
                .user(user)
                .champion(champion)
                .build();

        // then
        assertAll(
                () -> assertEquals(originalId, playlist.getOriginalId()),
                () -> assertEquals(provider, playlist.getProvider()),
                () -> assertEquals(title, playlist.getTitle()),
                () -> assertEquals(description, playlist.getDescription()),
                () -> assertEquals(image, playlist.getImage()),
                () -> assertEquals(externalUrl, playlist.getExternalUrl()),
                () -> assertEquals(0, playlist.getView()),
                () -> assertEquals(user, playlist.getUser()),
                () -> assertEquals(champion, playlist.getChampion())
        );
    }

    @Test
    @DisplayName("playlist의 조회수를 1 증가시킨다. - 성공")
    void addView() {

        // given
        String originalId = "1234";
        Provider provider = Provider.YOUTUBE;
        String title = "테스트 플레이 리스트";
        String description = "테스트 설명";
        Image image = new Image("url", "640", "640");
        String externalUrl = "외부 url";
        User user = generateUser("test@email.com");
        Champion champion = generateChampion();

        // when
        Playlist playlist = Playlist.builder()
                .originalId(originalId)
                .provider(provider)
                .title(title)
                .description(description)
                .image(image)
                .externalUrl(externalUrl)
                .user(user)
                .champion(champion)
                .build();

        // when
        playlist.addView();

        // then
        assertEquals(1, playlist.getView());
    }

    @Test
    @DisplayName("playlist를 update하는 테스트 - 성공")
    void update() {

        // given
        String originalId = "1234";
        Provider provider = Provider.YOUTUBE;
        String title = "테스트 플레이리스트";
        String description = "테스트 설명";
        Image image = new Image("url", "640", "640");
        String externalUrl = "외부 url";
        User user = generateUser("test@email.com");
        Champion champion = generateChampion();

        Playlist playlist = Playlist.builder()
                .originalId(originalId)
                .provider(provider)
                .title(title)
                .description(description)
                .image(image)
                .externalUrl(externalUrl)
                .user(user)
                .champion(champion)
                .build();

        String updateTitle = "업데이트 플레이리스트";
        String updateDescription = "업데이트 테스트 설명";
        Champion updateChampion = generateChampion();

        Playlist updatePlaylist = Playlist.builder()
                .title(updateTitle)
                .description(updateDescription)
                .champion(updateChampion)
                .build();

        // when
        playlist.update(updatePlaylist);

        // then
        assertAll(
                () -> assertEquals(originalId, playlist.getOriginalId()),
                () -> assertEquals(provider, playlist.getProvider()),
                () -> assertEquals(updateTitle, playlist.getTitle()),
                () -> assertEquals(updateDescription, playlist.getDescription()),
                () -> assertEquals(image, playlist.getImage()),
                () -> assertEquals(externalUrl, playlist.getExternalUrl()),
                () -> assertEquals(user, playlist.getUser()),
                () -> assertEquals(updateChampion, playlist.getChampion())
        );
    }

    @Test
    @DisplayName("playlist의 user를 변경하는 테스트 - 성공")
    void changeUser() {

        // given
        String originalId = "1234";
        Provider provider = Provider.YOUTUBE;
        String title = "테스트 플레이리스트";
        String description = "테스트 설명";
        Image image = new Image("url", "640", "640");
        String externalUrl = "외부 url";
        User user = generateUser("test@email.com");
        Champion champion = generateChampion();

        Playlist playlist = Playlist.builder()
                .originalId(originalId)
                .provider(provider)
                .title(title)
                .description(description)
                .image(image)
                .externalUrl(externalUrl)
                .user(user)
                .champion(champion)
                .build();

        User changeUser = generateUser("change-user@email.com");

        // when
        playlist.changeUser(changeUser);

        // then
        assertEquals(changeUser, playlist.getUser());
    }
}