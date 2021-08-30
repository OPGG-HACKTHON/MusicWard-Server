package io.github.opgg.music_ward_server.dto.search.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;


@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchSummonerResponse {
    private String summonerName;
    private String winType;
    private String favoriteChampion;


    public SearchSummonerResponse(String summonerName, String favoriteChampion, String winType) {
        this.summonerName = summonerName;
        this.winType = winType;
        this.favoriteChampion = favoriteChampion;
    }

}
