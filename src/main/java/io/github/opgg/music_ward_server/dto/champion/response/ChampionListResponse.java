package io.github.opgg.music_ward_server.dto.champion.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChampionListResponse {

    private List<ChampionListDTO> championList;

    public ChampionListResponse(List<ChampionListDTO> championList){
        this.championList = championList;
    }

}
