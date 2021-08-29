package io.github.opgg.music_ward_server.entity.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Builder를 활용하여 Song 객체를 생성하는 테스트 - 성공")
    void createByBuilder() {
        //given
        Role role = Role.ROLE_ADMIN;
        String googleEmail = "test@gmail.com";
        String spotifyEmail = "test@gmail.com";
        String name = "test";
        String provider = "test";
        String nickname = "hideonbush";

        //when
        User user = User.builder()
                .role(role)
                .googleEmail(googleEmail)
                .spotifyEmail(spotifyEmail)
                .name(name)
                .provider(provider)
                .nickname(nickname)
                .withdrawal(false)
                .build();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(role, user.getRole()),
                () -> Assertions.assertEquals(googleEmail, user.getGoogleEmail()),
                () -> Assertions.assertEquals(spotifyEmail, user.getSpotifyEmail()),
                () -> Assertions.assertEquals(name, user.getName()),
                () -> Assertions.assertEquals(provider, user.getProvider()),
                () -> Assertions.assertEquals(nickname, user.getNickname())
        );
    }

}