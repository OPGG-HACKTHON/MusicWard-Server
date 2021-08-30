package io.github.opgg.music_ward_server.service.ranking;

import io.github.opgg.music_ward_server.dto.ranking.response.ChampionRankingResponse;
import io.github.opgg.music_ward_server.dto.ranking.response.PlaylistRankingResponse;
import io.github.opgg.music_ward_server.entity.champion.ChampionRepository;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RankingServiceImpl implements RankingService {

    private final ChampionRepository championRepository;
    private final PlaylistRepository playlistRepository;

    @Override
    public List<ChampionRankingResponse> getChampionRanking() {

        return championRepository.findAllDistinct()
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

    @Override
    public List<PlaylistRankingResponse> getPlaylistRanking() {

        return playlistRepository.findAllOrderByCreatedDate()
                .stream()
                .map(playlist -> new PlaylistRankingResponse(playlist))
                .sorted((o1, o2) -> {

                    int total1 = o1.getView() + o1.getWardsTotal() + o1.getCommentsTotal();
                    int total2 = o2.getView() + o2.getWardsTotal() + o2.getCommentsTotal();

                    if (total1 < total2) {
                        return 1;
                    }

                    return -1;
                })
                .limit(10)
                .collect(Collectors.toList());
    }
}