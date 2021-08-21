package io.github.opgg.music_ward_server.service.playlist;

import io.github.opgg.music_ward_server.dto.playlist.NonPlaylistsResponse;

public interface PlaylistService {
    NonPlaylistsResponse getNonPlaylists();
}