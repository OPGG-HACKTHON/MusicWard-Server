package io.github.opgg.music_ward_server.service.search;

import io.github.opgg.music_ward_server.dto.playlist.response.PlaylistMainResponse;
import io.github.opgg.music_ward_server.dto.search.response.SearchSummonerResponse;
import io.github.opgg.music_ward_server.entity.playlist.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SearchService {

    SearchSummonerResponse getRiotSummonerInfo(String summonerName);
    Page<PlaylistMainResponse> findByChampionName(String championName, Provider provider, Pageable pageable);
    Page<PlaylistMainResponse> findByPlaylistTitle(String title, Provider provider, Pageable pageable);
    Page<PlaylistMainResponse> findByTagTitle(String title, Provider provider, Pageable pageable);
}