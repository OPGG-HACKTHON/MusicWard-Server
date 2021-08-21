package io.github.opgg.music_ward_server.dto.champion.response;

import io.github.opgg.music_ward_server.entity.champion.Champion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;

@Getter
@AllArgsConstructor
public class ChampionListDTO {

    @Column(name = "champion_id")
    private Long championId;

    private String englishName;

    private String name;

    private String position;

    private String imageUrl;

}
