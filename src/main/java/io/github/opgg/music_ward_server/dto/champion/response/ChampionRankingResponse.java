package io.github.opgg.music_ward_server.dto.champion.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ChampionRankingResponse {

    private ChampionMainResponse champion;
    private int view;
    private int wardsTotal;
    private int commentsTotal;

    public ChampionRankingResponse(Champion champion, int view, int wardsTotal, int commentsTotal) {
        this.champion = new ChampionMainResponse(champion);
        this.view = view;
        this.wardsTotal = wardsTotal;
        this.commentsTotal = commentsTotal;
    }
}