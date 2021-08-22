package io.github.opgg.music_ward_server.service.playlist;

import io.github.opgg.music_ward_server.dto.playlist.response.NonPlaylistsResponse;
import io.github.opgg.music_ward_server.dto.playlist.response.PlaylistMainResponse;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistSaveRequest;
import io.github.opgg.music_ward_server.dto.track.request.TrackSaveRequest;
import io.github.opgg.music_ward_server.dto.track.response.TrackMainResponse;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.champion.ChampionRepository;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.PlaylistRepository;
import io.github.opgg.music_ward_server.entity.tag.Tag;
import io.github.opgg.music_ward_server.entity.tag.TagRepository;
import io.github.opgg.music_ward_server.entity.token.Token;
import io.github.opgg.music_ward_server.entity.token.TokenRepository;
import io.github.opgg.music_ward_server.entity.token.Type;
import io.github.opgg.music_ward_server.entity.track.Track;
import io.github.opgg.music_ward_server.entity.track.TrackRepository;
import io.github.opgg.music_ward_server.entity.user.User;
import io.github.opgg.music_ward_server.entity.user.UserRepository;
import io.github.opgg.music_ward_server.exception.ChampionNotFoundException;
import io.github.opgg.music_ward_server.exception.EmptyRefreshTokenException;
import io.github.opgg.music_ward_server.exception.UserNotFoundException;
import io.github.opgg.music_ward_server.service.user.UserService;
import io.github.opgg.music_ward_server.utils.api.client.google.GoogleApiClient;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleAccessTokenResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.YoutubePlaylistResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.YoutubePlaylistsResponse;
import io.github.opgg.music_ward_server.utils.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final UserService userService;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final TrackRepository trackRepository;
    private final PlaylistRepository playlistRepository;
    private final ChampionRepository championRepository;
    private final GoogleApiClient googleApiClient;

    @Override
    @Transactional
    public PlaylistMainResponse save(PlaylistSaveRequest requestDto) {

        Long userId = SecurityUtil.getCurrentUserId();
        User user = getUser(userId);

        Champion champion = getChampion(requestDto.getChampionName());

        Token googleRefreshToken = tokenRepository.findById(userId + Type.GOOGLE.name())
                .orElseThrow(EmptyRefreshTokenException::new);

        GoogleAccessTokenResponse accessToken = userService.getAccessToken(googleRefreshToken.getRefreshToken());

        YoutubePlaylistResponse nonPlaylist = googleApiClient.getPlaylist(accessToken.getAccessTokenAndTokenType(),
                "id,snippet,contentDetails,status", requestDto.getOriginalId(), "50");

        Playlist playlist = playlistRepository.save(requestDto.toEntity(nonPlaylist.getImage(), user, champion));

        for (String tag : requestDto.getTags()) {
            Tag buildTag = Tag.builder()
                    .title(tag)
                    .playlist(playlist)
                    .build();
            tagRepository.save(buildTag);
        }

        List<TrackSaveRequest> trackSaveRequests = nonPlaylist.getTrackSaveRequests();
        for (TrackSaveRequest trackSaveRequest : trackSaveRequests) {
            trackRepository.save(trackSaveRequest.toEntity(playlist));
        }

        List<Track> tracks = trackRepository.findByPlaylistId(playlist.getId());

        List<TrackMainResponse> trackMainResponses = tracks.stream()
                .map(TrackMainResponse::new)
                .collect(Collectors.toList());

        return new PlaylistMainResponse(playlist, requestDto.getTags(), trackMainResponses);
    }

    @Override
    public NonPlaylistsResponse getNonPlaylists() {

        Long userId = SecurityUtil.getCurrentUserId();

        Token googleRefreshToken = tokenRepository.findById(userId + Type.GOOGLE.name())
                .orElseThrow(EmptyRefreshTokenException::new);

        GoogleAccessTokenResponse accessToken = userService.getGoogleAccessToken(googleRefreshToken.getRefreshToken());

        YoutubePlaylistsResponse playlists = googleApiClient.getPlaylists(
                accessToken.getAccessTokenAndTokenType(), "id,snippet,status", true);

        return playlists.toNonPlaylists();
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    private Champion getChampion(String championName) {
        return championRepository.findByName(championName).orElseThrow(ChampionNotFoundException::new);
    }
}