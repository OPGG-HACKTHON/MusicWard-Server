package io.github.opgg.music_ward_server.dto.champion.response;

import io.github.opgg.music_ward_server.entity.champion.Champion;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChampionDetailDTO {

    private Long championId;

    private String englishName;

    private String name;

    private String position;

    private String imageUrl;

    private String profileImageUrl;

    private String story;

    private String title;

    public ChampionDetailDTO(Champion champion) {
        this.championId = champion.getId();
        this.story = champion.getStory();
        this.title = champion.getTitle();
        this.englishName = champion.getEnglishName();
        this.position = champion.getPosition();
        this.profileImageUrl = champion.getProfileImageUrl();
        this.imageUrl = champion.getImageUrl();
        this.name = champion.getName();
    }
}
