package io.github.opgg.music_ward_server.service.champion;

import io.github.opgg.music_ward_server.dto.champion.response.ChampionDetailDTO;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionListResponse;

public interface ChampionService {
    ChampionListResponse getChampionList();
    ChampionDetailDTO getChampionDetail(Long championId);
}