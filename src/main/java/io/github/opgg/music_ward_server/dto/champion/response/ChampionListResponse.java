package io.github.opgg.music_ward_server.dto.champion.response;

import io.github.opgg.music_ward_server.entity.champion.Champion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Setter
public class ChampionListResponse {
    private List<ChampionListDTO> championList;

    public ChampionListResponse() {

    }

    public void setChampionList(List<Champion> champion){
        this.championList = champion.stream()
                .map(Champion::toDTO)
                .collect(Collectors.toList());
    }
}
