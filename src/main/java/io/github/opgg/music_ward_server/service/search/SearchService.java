package io.github.opgg.music_ward_server.service.search;


import io.github.opgg.music_ward_server.dto.search.response.SearchSummonerResponse;


public interface SearchService {
    SearchSummonerResponse getRiotSummonerInfo(String summonerName);
}
