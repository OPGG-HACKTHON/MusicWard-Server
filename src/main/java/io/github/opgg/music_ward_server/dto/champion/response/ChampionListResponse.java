package io.github.opgg.music_ward_server.dto.champion.response;

import lombok.Getter;
import java.util.List;

@Getter
public class ChampionListResponse {

    private List<ChampionListDTO> championList;

    public ChampionListResponse(List<ChampionListDTO> championList){
        this.championList = championList;
    }

}
