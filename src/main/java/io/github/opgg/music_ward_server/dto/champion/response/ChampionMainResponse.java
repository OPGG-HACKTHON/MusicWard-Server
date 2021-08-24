package io.github.opgg.music_ward_server.dto.champion.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChampionMainResponse {

    private final Long championId;
    private final String name;
    private final String title;
    private final String englishName;
    private final String story;
    private final List<String> positions;
    private final String profileImageUrl;
    private final String imageUrl;

    public ChampionMainResponse(Champion champion) {
        this.championId = champion.getId();
        this.name = champion.getName();
        this.title = champion.getTitle();
        this.englishName = champion.getEnglishName();
        this.story = champion.getStory();

        String[] splitPositions = champion.getPosition().split(",");
        this.positions = new ArrayList<>(Arrays.asList(splitPositions));

        this.profileImageUrl = champion.getProfileImageUrl();
        this.imageUrl = champion.getImageUrl();
    }
}
