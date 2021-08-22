package io.github.opgg.music_ward_server.service.playlist;

import io.github.opgg.music_ward_server.dto.playlist.response.NonPlaylistsResponse;
import io.github.opgg.music_ward_server.dto.playlist.response.PlaylistMainResponse;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistSaveRequest;

public interface PlaylistService {
    PlaylistMainResponse save(PlaylistSaveRequest request);
    NonPlaylistsResponse getNonPlaylists();
}