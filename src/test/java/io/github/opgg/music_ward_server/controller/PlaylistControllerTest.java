package io.github.opgg.music_ward_server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.opgg.music_ward_server.BaseIntegrationTest;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistSaveRequest;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.champion.ChampionRepository;
import io.github.opgg.music_ward_server.entity.playlist.Image;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.PlaylistRepository;
import io.github.opgg.music_ward_server.entity.playlist.Provider;
import io.github.opgg.music_ward_server.entity.user.Role;
import io.github.opgg.music_ward_server.entity.user.User;
import io.github.opgg.music_ward_server.entity.user.UserRepository;
import io.github.opgg.music_ward_server.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlaylistControllerTest extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    static User generateUser(String email) {
        return User.builder()
                .googleEmail(email)
                .spotifyEmail(email)
                .name("test")
                .role(Role.ROLE_ADMIN)
                .nickname("hideonbush")
                .withdrawal(false)
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

    static Playlist generatePlaylist(User user, Champion champion) {
        return Playlist.builder()
                .originalId("1234")
                .provider(Provider.YOUTUBE)
                .title("테스트 플레이 리스트")
                .description("테스트 플레이 리스트 설명")
                .image(new Image("url", "640", "640"))
                .externalUrl("외부 url")
                .user(user)
                .champion(champion)
                .build();
    }

    @Test
    @DisplayName("GET playlists/{playlistId}")
    void getPlaylist() throws Exception {

        // given
        User user = generateUser("test1@email.com");
        Champion champion = generateChampion();
        Playlist playlist = generatePlaylist(user, champion);

        // when
        userRepository.save(user);
        championRepository.save(champion);
        Playlist savePlaylist = playlistRepository.save(playlist);

        // then
        mockMvc.perform(get("/playlists/{playlistId}", savePlaylist.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET users/{userId}/playlists")
    void getUsersPlaylists() throws Exception {

        // given
        User user = generateUser("test2@email.com");
        Champion champion = generateChampion();

        userRepository.save(user);
        championRepository.save(champion);

        for (int i = 0; i < 10; i++) {
            playlistRepository.save(generatePlaylist(user, champion));
        }

        // when & then
        mockMvc.perform(get("/users/{userId}/playlists", user.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}