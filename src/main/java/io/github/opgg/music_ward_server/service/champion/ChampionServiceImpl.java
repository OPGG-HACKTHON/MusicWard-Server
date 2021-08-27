package io.github.opgg.music_ward_server.service.champion;

import io.github.opgg.music_ward_server.dto.champion.response.ChampionDetailDTO;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionListDTO;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionListResponse;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionRankingResponse;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.champion.ChampionRepository;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
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

    @Override
    public List<ChampionRankingResponse> getRanking() {

        return championRepository.findAll()
                .stream()
                .map(champion -> {
                    List<Playlist> playlists = champion.getPlaylists();

                    int view = 0;
                    int wardsTotal = 0;
                    int commentsTotal = 0;
                    for (Playlist playlist : playlists) {
                        view += playlist.getView();
                        wardsTotal += playlist.getWards().size();
                        commentsTotal += playlist.getComments().size();
                    }

                    return new ChampionRankingResponse(champion, view, wardsTotal, commentsTotal);
                })
                .sorted(((o1, o2) -> {

                    int total1 = o1.getView() + o1.getWardsTotal() + o1.getCommentsTotal();
                    int total2 = o2.getView() + o2.getWardsTotal() + o2.getCommentsTotal();

                    if (total1 < total2) {
                        return 1;
                    }

                    return -1;
                }))
                .limit(10)
                .collect(Collectors.toList());
    }
}