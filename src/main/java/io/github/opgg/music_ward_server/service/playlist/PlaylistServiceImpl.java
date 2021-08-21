package io.github.opgg.music_ward_server.service.playlist;

import io.github.opgg.music_ward_server.dto.playlist.NonPlaylistsResponse;
import io.github.opgg.music_ward_server.entity.token.Token;
import io.github.opgg.music_ward_server.entity.token.TokenRepository;
import io.github.opgg.music_ward_server.entity.token.Type;
import io.github.opgg.music_ward_server.exception.EmptyRefreshTokenException;
import io.github.opgg.music_ward_server.service.user.UserService;
import io.github.opgg.music_ward_server.utils.api.client.GoogleApiClient;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleAccessTokenResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.YoutubePlaylistsResponse;
import io.github.opgg.music_ward_server.utils.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final GoogleApiClient googleApiClient;

    @Override
    public NonPlaylistsResponse getNonPlaylists() {

        Long userId = SecurityUtil.getCurrentUserId();

        Token googleRefreshToken = tokenRepository.findById(userId + Type.GOOGLE.name())
                .orElseThrow(EmptyRefreshTokenException::new);

        GoogleAccessTokenResponse accessToken = userService.getAccessToken(googleRefreshToken.getRefreshToken());

        YoutubePlaylistsResponse playlists = googleApiClient.getPlaylists(
                accessToken.getAccessTokenAndTokenType(), "id,snippet,status", true);

        return playlists.toNonPlaylists();
    }
}
