package io.github.opgg.music_ward_server.entity.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Builder를 활용하여 Song 객체를 생성하는 테스트 - 성공")
    void createByBuilder() {

        //given
        String googleEmail = "test@gmail.com";
        String spotifyEmail = "test@gmail.com";
        String name = "test";
        Role role = Role.ROLE_ADMIN;
        String nickname = "hideonbush";

        //when
        User user = User.builder()
                .googleEmail(googleEmail)
                .spotifyEmail(spotifyEmail)
                .name(name)
                .role(role)
                .nickname(nickname)
                .withdrawal(false)
                .build();

        //then
        assertAll(
                () -> assertEquals(googleEmail, user.getGoogleEmail()),
                () -> assertEquals(spotifyEmail, user.getSpotifyEmail()),
                () -> assertEquals(name, user.getName()),
                () -> assertEquals(role, user.getRole()),
                () -> assertEquals(nickname, user.getNickname())
        );
    }
}