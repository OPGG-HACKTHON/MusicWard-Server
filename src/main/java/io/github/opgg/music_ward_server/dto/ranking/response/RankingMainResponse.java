package io.github.opgg.music_ward_server.dto.ranking.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RankingMainResponse {

    private int ranking;
    private Long id;
    private String imageUrl;
    private String title;
    private String subTitle;
    private int view;
    private int wardsTotal;
    private int commentsTotal;
    private int tracksTotal;

    // champion ranking 전용
    public RankingMainResponse(Champion champion, int view, int wardsTotal, int commentsTotal, int tracksTotal) {
        this.id = champion.getId();
        this.imageUrl = champion.getImageUrl();
        this.title = champion.getName();
        this.subTitle = champion.getTitle();
        this.view = view;
        this.wardsTotal = wardsTotal;
        this.commentsTotal = commentsTotal;
        this.tracksTotal = tracksTotal;
    }

    // playlist ranking 전용
    public RankingMainResponse(Playlist playlist, int wardsTotal, int commentsTotal, int tracksTotal) {
        this.id = playlist.getId();
        this.imageUrl = playlist.getImage() != null ? playlist.getImage().getUrl() : null;
        this.title = playlist.getTitle();
        this.subTitle = playlist.getDescription();
        this.view = playlist.getView();
        this.wardsTotal = wardsTotal;
        this.commentsTotal = commentsTotal;
        this.tracksTotal = tracksTotal;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}