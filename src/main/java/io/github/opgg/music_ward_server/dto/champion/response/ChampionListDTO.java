package io.github.opgg.music_ward_server.dto.champion.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ChampionListDTO {

    private Long championId;

    private String englishName;

    private String name;

    private String position;

    private String imageUrl;

}
