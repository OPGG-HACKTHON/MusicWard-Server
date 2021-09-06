package io.github.opgg.music_ward_server.service.champion;

import io.github.opgg.music_ward_server.dto.champion.response.ChampionDetailDTO;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionListDTO;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionListResponse;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.champion.ChampionRepository;
import io.github.opgg.music_ward_server.exception.ChampionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChampionServiceImpl implements ChampionService {

    private final ChampionRepository championRepository;

    @Override
    public ChampionListResponse getChampionList(String positions, String championName, Sort sort) {
        List<ChampionListDTO> championListDTO;
        if (championName != null && positions != null) {
            championListDTO = championRepository.findByPositionContainingIgnoreCaseAndNameContaining(positions, championName, sort)
                    .stream()
                    .map(ChampionListDTO::new)
                    .collect(Collectors.toList());
        } else if (championName != null && positions == null) {
            championListDTO = championRepository.findByNameContaining(championName, sort)
                    .stream()
                    .map(ChampionListDTO::new)
                    .collect(Collectors.toList());
        } else if (championName == null && positions != null) {
            championListDTO = championRepository.findByPositionContainingIgnoreCase(positions, sort)
                    .stream()
                    .map(ChampionListDTO::new)
                    .collect(Collectors.toList());
        } else {
            championListDTO = championRepository.findAll(sort)
                    .stream()
                    .map(ChampionListDTO::new)
                    .collect(Collectors.toList());
        }
        return new ChampionListResponse(championListDTO);
    }

    @Override
    public ChampionDetailDTO getChampionDetail(Long championId) {
        Champion champion = championRepository.findById(championId).orElseThrow(ChampionNotFoundException::new);
        return new ChampionDetailDTO(champion);

    }


}