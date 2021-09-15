package io.github.opgg.music_ward_server.service.playlist;

import io.github.opgg.music_ward_server.dto.comment.response.CommentMainResponse;
import io.github.opgg.music_ward_server.dto.playlist.request.PlaylistReportRequest;
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
import io.github.opgg.music_ward_server.entity.playlist.Provider;
import io.github.opgg.music_ward_server.entity.report.Report;
import io.github.opgg.music_ward_server.entity.report.ReportRepository;
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
import io.github.opgg.music_ward_server.exception.NotYourPlaylistException;
import io.github.opgg.music_ward_server.exception.PlaylistNotFoundException;
import io.github.opgg.music_ward_server.exception.UnsupportedProviderException;
import io.github.opgg.music_ward_server.exception.UserNotFoundException;
import io.github.opgg.music_ward_server.service.user.UserService;
import io.github.opgg.music_ward_server.utils.api.client.google.GoogleApiClient;
import io.github.opgg.music_ward_server.utils.api.client.spotify.SpotifyApiClient;
import io.github.opgg.music_ward_server.utils.api.dto.PlaylistResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.GoogleAccessTokenResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.YoutubePlaylistResponse;
import io.github.opgg.music_ward_server.utils.api.dto.google.YoutubePlaylistsResponse;
import io.github.opgg.music_ward_server.utils.api.dto.spotify.SpotifyAccessTokenResponse;
import io.github.opgg.music_ward_server.utils.api.dto.spotify.SpotifyPlaylistResponse;
import io.github.opgg.music_ward_server.utils.api.dto.spotify.SpotifyPlaylistsResponse;
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
    private final ReportRepository reportRepository;
    private final GoogleApiClient googleApiClient;
    private final SpotifyApiClient spotifyApiClient;

    @Override
    public NonPlaylistsResponse getNonPlaylists(String provider) {

        Long userId = SecurityUtil.getCurrentUserId();

        if (Provider.toProvider(provider) == Provider.YOUTUBE) {
            Token token = tokenRepository.findById(userId + Type.GOOGLE.name())
                    .orElseThrow(EmptyRefreshTokenException::new);

            GoogleAccessTokenResponse accessToken = userService.getGoogleAccessToken(token.getRefreshToken());

            YoutubePlaylistsResponse playlists = googleApiClient.getPlaylists(
                    accessToken.getAccessTokenAndTokenType(), "id,snippet,status", true);

            return playlists.toNonPlaylists();

        } else if (Provider.toProvider(provider) == Provider.SPOTIFY) {
            Token token = tokenRepository.findById(userId + Type.SPOTIFY.name())
                    .orElseThrow(EmptyRefreshTokenException::new);

            SpotifyAccessTokenResponse accessToken = userService.getSpotifyAccessToken(token.getRefreshToken());

            SpotifyPlaylistsResponse playlists = spotifyApiClient.getPlaylists(
                    accessToken.getAccessTokenAndTokenType());

            return playlists.toNonPlaylists();

        } else {
            throw new UnsupportedProviderException();
        }
    }

    @Override
    @Transactional
    public PlaylistMainResponse save(PlaylistSaveRequest requestDto) {

        Long userId = SecurityUtil.getCurrentUserId();
        User user = getUser(userId);

        Champion champion = getChampion(requestDto.getChampionName());

        Provider provider = Provider.toProvider(requestDto.getProvider());

        Playlist playlist;
        PlaylistResponse nonPlaylist;
        if (provider == Provider.YOUTUBE) {
            Token refreshToken = tokenRepository.findById(userId + Type.GOOGLE.name())
                    .orElseThrow(EmptyRefreshTokenException::new);

            GoogleAccessTokenResponse accessToken = userService.getGoogleAccessToken(refreshToken.getRefreshToken());

            nonPlaylist = googleApiClient.getPlaylist(accessToken.getAccessTokenAndTokenType(),
                    "id,snippet,contentDetails,status", requestDto.getOriginalId(), "50");

            playlist = playlistRepository.save(requestDto.toEntity(nonPlaylist.getImage(), user, champion));

        } else if (provider == Provider.SPOTIFY) {
            Token refreshToken = tokenRepository.findById(userId + Type.SPOTIFY.name())
                    .orElseThrow(EmptyRefreshTokenException::new);

            SpotifyAccessTokenResponse accessToken = userService.getSpotifyAccessToken(refreshToken.getRefreshToken());

            nonPlaylist = spotifyApiClient.getPlaylist(
                    accessToken.getAccessTokenAndTokenType(), requestDto.getOriginalId());

            playlist = playlistRepository.save(requestDto.toEntity(nonPlaylist.getImage(), user, champion));

        } else {
            throw new UnsupportedProviderException();
        }

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

        return new PlaylistMainResponse(
                playlist, requestDto.getTags(), null, null, trackMainResponses
        );
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

        Long userId = SecurityUtil.getCurrentUserId();

        Playlist playlist = getPlaylistWithUser(playlistId, userId);

        trackRepository.deleteByPlaylistId(playlistId);

        if (playlist.getProvider() == Provider.YOUTUBE) {

            Token token = tokenRepository.findById(userId + Type.GOOGLE.name())
                    .orElseThrow(EmptyRefreshTokenException::new);

            GoogleAccessTokenResponse accessToken = userService.getGoogleAccessToken(token.getRefreshToken());

            YoutubePlaylistResponse nonPlaylist = googleApiClient.getPlaylist(accessToken.getAccessTokenAndTokenType(),
                    "id,snippet,contentDetails,status", playlist.getOriginalId(), "50");

            List<TrackSaveRequest> trackSaveRequests = nonPlaylist.getTrackSaveRequests();
            for (TrackSaveRequest trackSaveRequest : trackSaveRequests) {
                trackRepository.save(trackSaveRequest.toEntity(playlist));
            }

        } else if (playlist.getProvider() == Provider.SPOTIFY) {

            Token token = tokenRepository.findById(userId + Type.SPOTIFY.name())
                    .orElseThrow(EmptyRefreshTokenException::new);

            SpotifyAccessTokenResponse accessToken = userService.getSpotifyAccessToken(token.getRefreshToken());

            SpotifyPlaylistResponse nonPlaylist = spotifyApiClient.getPlaylist(
                    accessToken.getAccessTokenAndTokenType(), playlist.getOriginalId());

            List<TrackSaveRequest> trackSaveRequests = nonPlaylist.getTrackSaveRequests();
            for (TrackSaveRequest trackSaveRequest : trackSaveRequests) {
                trackRepository.save(trackSaveRequest.toEntity(playlist));
            }

        } else {
            throw new UnsupportedProviderException();
        }
    }

    @Override
    @Transactional
    public void delete(Long playlistId) {

        Long userId = SecurityUtil.getCurrentUserId();

        getPlaylistWithUser(playlistId, userId);

        trackRepository.deleteByPlaylistId(playlistId);
        tagRepository.deleteByPlaylistId(playlistId);
        wardRepository.deleteByPlaylistId(playlistId);
        commentRepository.deleteByPlaylistId(playlistId);

        playlistRepository.deleteByIdAndUserId(playlistId, userId);
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

        return toPlaylistMainResponse(playlists);
    }

    @Override
    @Transactional
    public void report(PlaylistReportRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = getUser(userId);
        Playlist playlist = getPlaylist(request.getPlaylistId());

        reportRepository.save(
                Report.builder()
                        .user(user)
                        .playlist(playlist)
                        .build()
        );
    }

    @Override
    public Page<PlaylistMainResponse> findWardingPlaylist(Pageable pageable) {
        Long userId = SecurityUtil.getCurrentUserId();

        Page<Playlist> playlists = playlistRepository.findByWardUserId(userId, pageable);

        return toPlaylistMainResponses(playlists);
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
        return playlistRepository.findByIdAndUserId(playlistId, userId).orElseThrow(NotYourPlaylistException::new);
    }

    private List<PlaylistMainResponse> toPlaylistMainResponse(List<Playlist> playlists) {

        return playlists.stream()
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
    }

    private Page<PlaylistMainResponse> toPlaylistMainResponses(Page<Playlist> playlists) {

        return playlists.map(playlist -> {
            List<String> tags = tagRepository.findByPlaylistId(playlist.getId())
                    .stream()
                    .map(tag -> tag.getTitle())
                    .collect(Collectors.toList());

            int wardTotal = wardRepository.countByPlaylistId(playlist.getId());
            int commentTotal = commentRepository.countByPlaylistId(playlist.getId());
            int trackTotal = trackRepository.countByPlaylistId(playlist.getId());

            return new PlaylistMainResponse(playlist, tags, wardTotal, commentTotal, trackTotal);
        });
    }
}