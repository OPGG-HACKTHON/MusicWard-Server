package io.github.opgg.music_ward_server.service.playlist;

import io.github.opgg.music_ward_server.dto.comment.response.CommentMainResponse;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistUpdateRequest;
import io.github.opgg.music_ward_server.dto.playlist.response.NonPlaylistsResponse;
import io.github.opgg.music_ward_server.dto.playlist.response.PlaylistMainResponse;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistSaveRequest;
import io.github.opgg.music_ward_server.dto.track.request.TrackSaveRequest;
import io.github.opgg.music_ward_server.dto.track.response.TrackMainResponse;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.champion.ChampionRepository;
import io.github.opgg.music_ward_server.entity.comment.Comment;
import io.github.opgg.music_ward_server.entity.comment.CommentRepository;
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
import io.github.opgg.music_ward_server.entity.ward.WardRepository;
import io.github.opgg.music_ward_server.exception.ChampionNotFoundException;
import io.github.opgg.music_ward_server.exception.EmptyRefreshTokenException;
import io.github.opgg.music_ward_server.exception.PlaylistNotFoundException;
import io.github.opgg.music_ward_server.exception.UserNotFoundException;
import io.github.opgg.music_ward_server.service.user.UserService;
import io.github.opgg.music_ward_server.utils.api.client.google.GoogleApiClient;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleAccessTokenResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.YoutubePlaylistResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.YoutubePlaylistsResponse;
import io.github.opgg.music_ward_server.utils.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final WardRepository wardRepository;
    private final TokenRepository tokenRepository;
    private final TrackRepository trackRepository;
    private final CommentRepository commentRepository;
    private final PlaylistRepository playlistRepository;
    private final ChampionRepository championRepository;
    private final GoogleApiClient googleApiClient;

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

    @Override
    @Transactional
    public PlaylistMainResponse save(PlaylistSaveRequest requestDto) {

        Long userId = SecurityUtil.getCurrentUserId();
        User user = getUser(userId);

        Champion champion = getChampion(requestDto.getChampionName());

        Token googleRefreshToken = tokenRepository.findById(userId + Type.GOOGLE.name())
                .orElseThrow(EmptyRefreshTokenException::new);

        GoogleAccessTokenResponse accessToken = userService.getGoogleAccessToken(googleRefreshToken.getRefreshToken());

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

        return new PlaylistMainResponse(playlist, requestDto.getTags(), null, null, trackMainResponses);
    }

    @Override
    public List<PlaylistMainResponse> findAll() {

        List<Playlist> playlists = playlistRepository.findAll();
        List<PlaylistMainResponse> playlistMainResponses = playlists.stream()
                .map(playlist -> {
                    List<Tag> findTags = tagRepository.findByPlaylistId(playlist.getId());
                    List<String> tags = findTags.stream()
                            .map(tag -> tag.getTitle())
                            .collect(Collectors.toList());

                    Integer wardTotal = wardRepository.countByPlaylistId(playlist.getId());
                    Integer commentTotal = commentRepository.countByPlaylistId(playlist.getId());
                    Integer trackTotal = trackRepository.countByPlaylistId(playlist.getId());

                    return new PlaylistMainResponse(playlist, tags, wardTotal, commentTotal, trackTotal);
                })
                .collect(Collectors.toList());

        return playlistMainResponses;
    }

    @Override
    public Page<PlaylistMainResponse> findByChampionName(String championName, Pageable pageable) {

        Page<Playlist> playlists = playlistRepository.findByChampionName(championName, pageable);
        Page<PlaylistMainResponse> playlistMainResponses = playlists.map(playlist -> {
            List<Tag> findTags = tagRepository.findByPlaylistId(playlist.getId());
            List<String> tags = findTags.stream()
                    .map(tag -> tag.getTitle())
                    .collect(Collectors.toList());

            Integer wardTotal = wardRepository.countByPlaylistId(playlist.getId());
            Integer commentTotal = commentRepository.countByPlaylistId(playlist.getId());
            Integer trackTotal = trackRepository.countByPlaylistId(playlist.getId());

            return new PlaylistMainResponse(playlist, tags, wardTotal, commentTotal, trackTotal);
        });

        return playlistMainResponses;
    }

    @Override
    public PlaylistMainResponse findById(Long playlistId) {

        Playlist playlist = getPlaylist(playlistId);

        List<Tag> findTags = tagRepository.findByPlaylistId(playlist.getId());
        List<String> tags = findTags.stream()
                .map(tag -> tag.getTitle())
                .collect(Collectors.toList());

        Integer wardTotal = wardRepository.countByPlaylistId(playlist.getId());

        List<Track> tracks = trackRepository.findByPlaylistId(playlist.getId());
        List<TrackMainResponse> trackMainResponses = tracks.stream()
                .map(TrackMainResponse::new)
                .collect(Collectors.toList());

        List<Comment> findComments = commentRepository.findByPlaylistId(playlist.getId());
        List<CommentMainResponse> comments = findComments.stream()
                .map(comment -> new CommentMainResponse(comment))
                .collect(Collectors.toList());

        return new PlaylistMainResponse(playlist, tags, wardTotal, comments, trackMainResponses);
    }

    @Override
    @Transactional
    public void update(Long playlistId, PlaylistUpdateRequest updateDto) {

        Long userId = SecurityUtil.getCurrentUserId();
        Playlist playlist = getPlaylistWithUser(playlistId, userId);
        Champion champion = getChampion(updateDto.getChampionName());

        tagRepository.deleteByPlaylistId(playlistId);
        for (String tag : updateDto.getTags()) {
            Tag buildTag = Tag.builder()
                    .title(tag)
                    .playlist(playlist)
                    .build();
            tagRepository.save(buildTag);
        }

        playlist.update(updateDto.toEntity(champion));
    }

    @Override
    @Transactional
    public void synchronize(Long playlistId) {

        trackRepository.deleteByPlaylistId(playlistId);

        Playlist playlist = getPlaylist(playlistId);

        Long userId = SecurityUtil.getCurrentUserId();

        Token googleRefreshToken = tokenRepository.findById(userId + Type.GOOGLE.name())
                .orElseThrow(EmptyRefreshTokenException::new);

        GoogleAccessTokenResponse accessToken = userService.getGoogleAccessToken(googleRefreshToken.getRefreshToken());

        YoutubePlaylistResponse nonPlaylist = googleApiClient.getPlaylist(accessToken.getAccessTokenAndTokenType(),
                "id,snippet,contentDetails,status", playlist.getOriginalId(), "50");

        List<TrackSaveRequest> trackSaveRequests = nonPlaylist.getTrackSaveRequests();
        for (TrackSaveRequest trackSaveRequest : trackSaveRequests) {
            trackRepository.save(trackSaveRequest.toEntity(playlist));
        }
    }

    @Override
    @Transactional
    public void delete(Long playlistId) {

        trackRepository.deleteByPlaylistId(playlistId);
        tagRepository.deleteByPlaylistId(playlistId);
        wardRepository.deleteByPlaylistId(playlistId);

        playlistRepository.deleteById(playlistId);
    }

    @Override
    @Transactional
    public void addView(Long playlistId) {

        Playlist playlist = getPlaylist(playlistId);
        playlist.addView();
    }

    @Override
    public List<PlaylistMainResponse> findByUserId(Long userId) {

        List<Playlist> playlists = playlistRepository.findByUserId(userId);
        List<PlaylistMainResponse> playlistMainResponses = playlists.stream()
                .map(playlist -> {
                    List<Tag> findTags = tagRepository.findByPlaylistId(playlist.getId());
                    List<String> tags = findTags.stream()
                            .map(tag -> tag.getTitle())
                            .collect(Collectors.toList());

                    Integer wardTotal = wardRepository.countByPlaylistId(playlist.getId());
                    Integer commentTotal = commentRepository.countByPlaylistId(playlist.getId());
                    Integer trackTotal = trackRepository.countByPlaylistId(playlist.getId());

                    return new PlaylistMainResponse(playlist, tags, wardTotal, commentTotal, trackTotal);
                })
                .collect(Collectors.toList());

        return playlistMainResponses;
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    private Champion getChampion(String championName) {
        return championRepository.findByName(championName).orElseThrow(ChampionNotFoundException::new);
    }

    private Playlist getPlaylist(Long id) {
        return playlistRepository.findById(id).orElseThrow(PlaylistNotFoundException::new);
    }

    private Playlist getPlaylistWithUser(Long playlistId, Long userId) {
        return playlistRepository.findByIdAndUserId(playlistId, userId).orElseThrow(PlaylistNotFoundException::new);
    }
}