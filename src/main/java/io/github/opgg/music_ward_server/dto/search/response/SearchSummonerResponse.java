package io.github.opgg.music_ward_server.dto.search.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.opgg.music_ward_server.dto.playlist.response.PlaylistMainResponse;
import lombok.Getter;
import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchSummonerResponse {

    private String summonerName;
    private String winType;
    private List<PlaylistMainResponse> winPlaylists;
    private String favoriteChampion;
    private List<PlaylistMainResponse> championPlaylists;

    public SearchSummonerResponse(String summonerName, String favoriteChampion, String winType, List<PlaylistMainResponse> championPlaylists, List<PlaylistMainResponse> winPlaylists) {
        this.summonerName = summonerName;
        this.winType = winType;
        this.favoriteChampion = favoriteChampion;
        this.championPlaylists = championPlaylists;
        this.winPlaylists = winPlaylists;
    }
}