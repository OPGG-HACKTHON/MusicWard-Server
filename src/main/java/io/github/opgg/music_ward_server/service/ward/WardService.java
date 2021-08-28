package io.github.opgg.music_ward_server.service.ward;

import io.github.opgg.music_ward_server.dto.ward.request.PostWardRequest;
import io.github.opgg.music_ward_server.dto.ward.request.RemoveWardRequest;

public interface WardService {
    void postWard(PostWardRequest request);
    void removeWard(RemoveWardRequest request);
}
