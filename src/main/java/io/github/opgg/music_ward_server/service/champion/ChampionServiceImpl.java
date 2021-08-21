package io.github.opgg.music_ward_server.service.champion;

import io.github.opgg.music_ward_server.dto.champion.response.ChampionDetailDTO;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionListDTO;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionListResponse;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.champion.ChampionRepository;
import io.github.opgg.music_ward_server.exception.ChampionNotFoundException;
import io.github.opgg.music_ward_server.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChampionServiceImpl implements ChampionService {
    private final ChampionRepository championRepository;

    @Override
    public ChampionListResponse getChampionList() {
        List<Champion> champions = championRepository.findAll();
        ChampionListResponse championListResponse = new ChampionListResponse();
        championListResponse.setChampionList(champions);
        return championListResponse;
    }

    @Override
    public ChampionDetailDTO getChampionDetail(Long championId) {
        Optional<Champion> champion = championRepository.findById(championId);
        champion.orElseThrow(ChampionNotFoundException::new);
        return new ChampionDetailDTO(champion);
    }

}
