package io.github.opgg.music_ward_server.service.ranking;

import io.github.opgg.music_ward_server.dto.ranking.response.RankingMainResponse;

import java.util.List;

public interface RankingService {

    List<RankingMainResponse> getChampionRanking();
    List<RankingMainResponse> getPlaylistRanking();
}