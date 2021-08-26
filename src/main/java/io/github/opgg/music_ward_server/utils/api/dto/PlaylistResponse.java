package io.github.opgg.music_ward_server.utils.api.dto;

import io.github.opgg.music_ward_server.dto.track.request.TrackSaveRequest;
import io.github.opgg.music_ward_server.entity.playlist.Image;

import java.util.List;

public interface PlaylistResponse {

    Image getImage();
    List<TrackSaveRequest> getTrackSaveRequests();
}