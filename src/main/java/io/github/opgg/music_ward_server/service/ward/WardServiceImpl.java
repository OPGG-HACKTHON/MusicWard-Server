package io.github.opgg.music_ward_server.service.ward;

import io.github.opgg.music_ward_server.dto.ward.request.PostWardRequest;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.PlaylistRepository;
import io.github.opgg.music_ward_server.entity.user.User;
import io.github.opgg.music_ward_server.entity.user.UserRepository;
import io.github.opgg.music_ward_server.entity.ward.Ward;
import io.github.opgg.music_ward_server.entity.ward.WardRepository;
import io.github.opgg.music_ward_server.exception.PlaylistNotFoundException;
import io.github.opgg.music_ward_server.exception.UserNotFoundException;
import io.github.opgg.music_ward_server.utils.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WardServiceImpl implements WardService {

    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final WardRepository wardRepository;

    @Override
    public void postWard(PostWardRequest request) {
        User user = getCurrentUser();
        Playlist playlist = playlistRepository.findById(request.getPlaylistId())
                .orElseThrow(PlaylistNotFoundException::new);

        wardRepository.save(
                Ward.builder()
                .user(user)
                .playlist(playlist)
                .build()
        );
    }

    private User getCurrentUser() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(UserNotFoundException::new);
    }

}
