package io.github.opgg.music_ward_server.service.search;

import io.github.opgg.music_ward_server.dto.playlist.response.PlaylistMainResponse;
import io.github.opgg.music_ward_server.dto.search.response.SearchSummonerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SearchService {

    SearchSummonerResponse getRiotSummonerInfo(String summonerName);
    Page<PlaylistMainResponse> findByChampionName(String championName, Pageable pageable);
    Page<PlaylistMainResponse> findByPlaylistTitle(String title,  Pageable pageable);
    Page<PlaylistMainResponse> findByTagTitle(String title, Pageable pageable);
}