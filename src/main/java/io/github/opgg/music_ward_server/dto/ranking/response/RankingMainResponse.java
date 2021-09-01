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

    // champion ranking 전용
    public RankingMainResponse(Champion champion, int view, int wardsTotal, int commentsTotal) {
        this.id = champion.getId();
        this.imageUrl = champion.getImageUrl();
        this.title = champion.getName();
        this.subTitle = champion.getStory();
        this.view = view;
        this.wardsTotal = wardsTotal;
        this.commentsTotal = commentsTotal;
    }

    // playlist ranking 전용
    public RankingMainResponse(Playlist playlist) {
        this.id = playlist.getId();
        this.imageUrl = playlist.getImage().getUrl();
        this.title = playlist.getTitle();
        this.subTitle = playlist.getDescription();
        this.view = playlist.getView();
        this.wardsTotal = playlist.getWards().size();
        this.commentsTotal = playlist.getComments().size();
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}