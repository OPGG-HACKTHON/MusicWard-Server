package io.github.opgg.music_ward_server.service.playlist;

import io.github.opgg.music_ward_server.dto.playlist.NonPlaylistsResponse;
import io.github.opgg.music_ward_server.dto.playlist.PlaylistMainResponse;
import io.github.opgg.music_ward_server.dto.playlist.PlaylistSaveRequest;

public interface PlaylistService {
    PlaylistMainResponse save(PlaylistSaveRequest request);
    NonPlaylistsResponse getNonPlaylists();
}