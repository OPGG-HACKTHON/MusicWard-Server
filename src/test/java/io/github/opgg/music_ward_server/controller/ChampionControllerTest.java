package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.BaseIntegrationTest;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.champion.ChampionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChampionControllerTest extends BaseIntegrationTest {

    @Autowired
    private ChampionRepository championRepository;

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
    @DisplayName("GET /champion/{championId}")
    void getChampion() throws Exception {

        // given
        Champion champion = generateChampion();

        // when
        Champion saveChampion = championRepository.save(champion);

        // then
        mockMvc.perform(get("/champion/{championId}", saveChampion.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /championlist")
    void getChampions() throws Exception {

        // given
        for (int i = 0; i < 10; i++) {
            Champion champion = generateChampion();
            championRepository.save(champion);
        }

        // when & then
        mockMvc.perform(get("/championlist")
                        .param("champion_name", "가렌")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}