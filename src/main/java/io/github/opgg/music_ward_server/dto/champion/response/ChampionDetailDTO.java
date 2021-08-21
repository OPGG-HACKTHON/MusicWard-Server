package io.github.opgg.music_ward_server.dto.champion.response;

import io.github.opgg.music_ward_server.entity.champion.Champion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class ChampionDetailDTO {

    @Column(name = "champion_id")
    private Long championId;

    private String englishName;

    private String name;

    private String position;

    private String imageUrl;

    private String profileImageUrl;

    private String story;

    private String title;

    public ChampionDetailDTO(Optional<Champion> champion) {
        this.championId = champion.get().getId();
        this.story = champion.get().getStory();
        this.title = champion.get().getTitle();
        this.englishName = champion.get().getEnglishName();
        this.position = champion.get().getPosition();
        this.profileImageUrl = champion.get().getProfileImageUrl();
        this.imageUrl = champion.get().getImageUrl();
        this.name = champion.get().getName();
    }
}
