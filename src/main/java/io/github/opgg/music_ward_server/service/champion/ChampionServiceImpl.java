package io.github.opgg.music_ward_server.service.champion;

import io.github.opgg.music_ward_server.dto.champion.response.ChampionDetailDTO;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionListDTO;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionListResponse;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.champion.ChampionRepository;
import io.github.opgg.music_ward_server.exception.ChampionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChampionServiceImpl implements ChampionService {

    private final ChampionRepository championRepository;

    @Override
    public ChampionListResponse getChampionList() {

        List<ChampionListDTO> championListDTO = championRepository.findAll()
                .stream()
                .map(Champion::toDTO)
                .collect(Collectors.toList());
        return new ChampionListResponse(championListDTO);

    }

    @Override
    public ChampionDetailDTO getChampionDetail(Long championId) {
        Champion champion = championRepository.findById(championId).orElseThrow(ChampionNotFoundException::new);
        return new ChampionDetailDTO(champion);

    }

}
