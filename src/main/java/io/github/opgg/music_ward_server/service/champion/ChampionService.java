package io.github.opgg.music_ward_server.service.champion;

import io.github.opgg.music_ward_server.dto.champion.response.ChampionDetailDTO;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionListResponse;
import org.springframework.data.domain.Sort;

public interface ChampionService {
    ChampionListResponse getChampionList(Sort sort);
    ChampionDetailDTO getChampionDetail(Long championId);
}