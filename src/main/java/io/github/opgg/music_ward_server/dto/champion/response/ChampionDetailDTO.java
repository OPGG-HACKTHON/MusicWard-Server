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
public class ChampionDetailDTO {

    private final Long championId;

    private final String englishName;

    private final String name;

    private final List<String> position;

    private final String imageUrl;

    private final String profileImageUrl;

    private final String story;

    private final String title;

    public ChampionDetailDTO(Champion champion) {
        this.championId = champion.getId();
        this.story = champion.getStory();
        this.title = champion.getTitle();
        this.englishName = champion.getEnglishName();
        this.profileImageUrl = champion.getProfileImageUrl();
        this.imageUrl = champion.getImageUrl();
        this.name = champion.getName();
        String[] splitPositions = champion.getPosition().split(",");
        this.position = new ArrayList<>(Arrays.asList(splitPositions));
    }
}
