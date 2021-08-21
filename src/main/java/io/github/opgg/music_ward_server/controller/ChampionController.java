package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.dto.champion.response.ChampionDataDTO;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionDetailDTO;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionListResponse;
import io.github.opgg.music_ward_server.service.champion.ChampionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChampionController {
    private final ChampionService championService;

    @GetMapping("/championlist")
    public ChampionDataDTO getChampoinList() {
        return new ChampionDataDTO<ChampionListResponse>(championService.getChampionList());
    }

    @GetMapping("/champion/{championId}")
    public ChampionDataDTO getChampionDetail(@PathVariable("championId") Long championId) {
        return new ChampionDataDTO<ChampionDetailDTO>(championService.getChampionDetail(championId));
    }
}
