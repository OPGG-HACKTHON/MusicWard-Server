package io.github.opgg.music_ward_server.dto.champion.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChampionListDTO {

    private Long championId;

    private String englishName;

    private String name;

    private List<String> positions;

    private String imageUrl;

    public ChampionListDTO(Champion champion) {
        this.championId = champion.getId();
        this.englishName = champion.getEnglishName();
        this.name = champion.getName();

        String[] splitPositions = champion.getPosition().split(",");
        this.positions = new ArrayList<>(Arrays.asList(splitPositions));

        this.imageUrl = champion.getImageUrl();
    }
}