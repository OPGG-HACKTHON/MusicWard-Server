package io.github.opgg.music_ward_server.service.ranking;

import io.github.opgg.music_ward_server.dto.ranking.response.ChampionRankingResponse;
import io.github.opgg.music_ward_server.dto.ranking.response.PlaylistRankingResponse;

import java.util.List;

public interface RankingService {

    List<ChampionRankingResponse> getChampionRanking();
    List<PlaylistRankingResponse> getPlaylistRanking();
}