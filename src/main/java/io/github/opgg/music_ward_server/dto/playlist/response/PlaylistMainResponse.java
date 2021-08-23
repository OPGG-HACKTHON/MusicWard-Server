package io.github.opgg.music_ward_server.dto.playlist.response;

import io.github.opgg.music_ward_server.dto.champion.response.ChampionMainResponse;
import io.github.opgg.music_ward_server.dto.track.response.TrackMainResponse;
import io.github.opgg.music_ward_server.entity.playlist.Image;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.Provider;
import lombok.Getter;

import java.util.List;

@Getter
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
    private final List<TrackMainResponse> items;

    public PlaylistMainResponse(Playlist playlist, List<String> tags, List<TrackMainResponse> tracks) {
        this.playlistId = playlist.getId();
        this.provider = playlist.getProvider();
        this.originalId = playlist.getOriginalId();
        this.title = playlist.getTitle();
        this.description = playlist.getDescription();
        this.image = playlist.getImage();
        this.champion = new ChampionMainResponse(playlist.getChampion());
        this.externalUrl = playlist.getExternalUrl();
        this.tags = tags;
        this.items = tracks;
    }
}