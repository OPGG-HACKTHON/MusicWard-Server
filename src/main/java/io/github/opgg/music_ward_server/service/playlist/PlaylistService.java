package io.github.opgg.music_ward_server.service.playlist;

import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistReportRequest;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistUpdateRequest;
import io.github.opgg.music_ward_server.dto.playlist.response.NonPlaylistsResponse;
import io.github.opgg.music_ward_server.dto.playlist.response.PlaylistMainResponse;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistSaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaylistService {
    NonPlaylistsResponse getNonPlaylists(String provider);
    PlaylistMainResponse save(PlaylistSaveRequest request);
    PlaylistMainResponse findById(Long playlistId);
    void update(Long playlistId, PlaylistUpdateRequest request);
    void synchronize(Long playlistId);
    void delete(Long playlistId);
    void addView(Long playlistId);
    List<PlaylistMainResponse> findByUserId(Long userId);
    void report(PlaylistReportRequest request);
    Page<PlaylistMainResponse> findWardingPlaylist(Pageable pageable);
}