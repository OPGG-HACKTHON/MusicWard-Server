package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.BaseIntegrationTest;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.champion.ChampionRepository;
import io.github.opgg.music_ward_server.entity.playlist.Image;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.PlaylistRepository;
import io.github.opgg.music_ward_server.entity.playlist.Provider;
import io.github.opgg.music_ward_server.entity.user.User;
import io.github.opgg.music_ward_server.entity.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SearchControllerTest extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

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

    private User getUser() {
        return userRepository.findById(1L).get();
    }

    @Test
    @DisplayName("GET search/champion")
    void getRankingByChampion() throws Exception {

        // given
        User user = getUser();

        Champion champion = generateChampion();
        championRepository.save(champion);

        Playlist playlist = generatePlaylist(user, champion);
        playlistRepository.save(playlist);

        String searchType = "champion";
        String query = "가렌";
        int page = 1;
        int size = 5;
        String sort = "created_date";

        // when & then
        mockMvc.perform(get("/search/{searchType}", searchType)
                        .param("query", query)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET search/playlist")
    void getRankingByPlaylist() throws Exception {

        // given
        User user = getUser();

        Champion champion = generateChampion();
        championRepository.save(champion);

        Playlist playlist = generatePlaylist(user, champion);
        playlistRepository.save(playlist);

        String searchType = "playlist";
        String query = "테스트";
        int page = 1;
        int size = 5;
        String sort = "created_date";

        // when & then
        mockMvc.perform(get("/search/{searchType}", searchType)
                        .param("query", query)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET search/tag")
    void getRankingByTag() throws Exception {

        // given
        User user = getUser();

        Champion champion = generateChampion();
        championRepository.save(champion);

        Playlist playlist = generatePlaylist(user, champion);
        playlistRepository.save(playlist);

        String searchType = "tag";
        String query = "가렌";
        int page = 1;
        int size = 5;
        String sort = "created_date";

        // when & then
        mockMvc.perform(get("/search/{searchType}", searchType)
                        .param("query", query)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET search/{searchType} - UnsupportedSearchTypeException")
    void getRankingUnsupportedSearchTypeException() throws Exception {

        // given
        String searchType = "champion1";
        String query = "가렌";
        int page = 1;
        int size = 5;
        String sort = "created_date";

        // when & then
        mockMvc.perform(get("/search/{searchType}", searchType)
                        .param("query", query)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET search/{searchType} - UnsupportedSortTypeException")
    void getRankingUnsupportedSortTypeException() throws Exception {

        // given
        String searchType = "champion";
        String query = "가렌";
        int page = 1;
        int size = 5;
        String sort = "created";

        // when & then
        mockMvc.perform(get("/search/{searchType}", searchType)
                        .param("query", query)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}