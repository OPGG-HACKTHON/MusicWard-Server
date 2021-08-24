package io.github.opgg.music_ward_server.dto.playlist.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.opgg.music_ward_server.dto.champion.response.ChampionMainResponse;
import io.github.opgg.music_ward_server.dto.comment.response.CommentMainResponse;
import io.github.opgg.music_ward_server.dto.track.response.TrackMainResponse;
import io.github.opgg.music_ward_server.entity.playlist.Image;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.Provider;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlaylistMainResponse {

    private final Long playlistId;
    private final Provider provider;
    private final String originalId;
    private final String title;
    private final String description;
    private final Image image;
    private final ChampionMainResponse champion;
    private final String externalUrl;
    private final List<String> tags;
    private final Comments comments;
    private final Wards wards;
    private final Items items;

    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private static class Comments {
        private final Integer total;
        private final List<CommentMainResponse> items;

        public Comments(Integer total) {
            this.total = total;
            this.items = new ArrayList<>();
        }

        public Comments(Integer total, List<CommentMainResponse> items) {
            this.total = total;
            this.items = items;
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private static class Wards {
        private final Integer total;

        public Wards(Integer total) {
            this.total = total;
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private static class Items {
        private final Integer total;
        private final List<TrackMainResponse> items;

        public Items(Integer total, List<TrackMainResponse> items) {
            this.total = total;
            this.items = items;
        }
    }

    // 단 건 조회
    public PlaylistMainResponse(Playlist playlist, List<String> tags, Integer wardTotal,
                                List<CommentMainResponse> comments, List<TrackMainResponse> tracks) {
        this.playlistId = playlist.getId();
        this.provider = playlist.getProvider();
        this.originalId = playlist.getOriginalId();
        this.title = playlist.getTitle();
        this.description = playlist.getDescription();
        this.image = playlist.getImage();
        this.champion = new ChampionMainResponse(playlist.getChampion());
        this.externalUrl = playlist.getExternalUrl();
        this.tags = tags;
        this.wards = new Wards(wardTotal);
        this.comments = new Comments(comments.size(), comments);
        this.items = new Items(tracks.size(), tracks);
    }

    // 목록 조회
    public PlaylistMainResponse(Playlist playlist, List<String> tags, Integer wardTotal,
                                Integer commentTotal, Integer trackTotal) {
        this.playlistId = playlist.getId();
        this.provider = playlist.getProvider();
        this.originalId = playlist.getOriginalId();
        this.title = playlist.getTitle();
        this.description = playlist.getDescription();
        this.image = playlist.getImage();
        this.champion = new ChampionMainResponse(playlist.getChampion());
        this.externalUrl = playlist.getExternalUrl();
        this.tags = tags;
        this.wards = new Wards(wardTotal);
        this.comments = new Comments(commentTotal);
        this.items = new Items(trackTotal, null);
    }
}