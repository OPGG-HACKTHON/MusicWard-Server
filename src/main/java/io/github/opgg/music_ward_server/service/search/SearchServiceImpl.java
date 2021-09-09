package io.github.opgg.music_ward_server.service.search;

import io.github.opgg.music_ward_server.dto.playlist.response.PlaylistMainResponse;
import io.github.opgg.music_ward_server.dto.search.response.SearchSummonerResponse;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.champion.ChampionRepository;
import io.github.opgg.music_ward_server.entity.comment.CommentRepository;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.PlaylistRepository;
import io.github.opgg.music_ward_server.entity.tag.TagRepository;
import io.github.opgg.music_ward_server.entity.track.TrackRepository;
import io.github.opgg.music_ward_server.entity.ward.WardRepository;
import io.github.opgg.music_ward_server.utils.api.client.riot.RiotMatchClient;
import io.github.opgg.music_ward_server.utils.api.client.riot.RiotSummonerClient;
import io.github.opgg.music_ward_server.utils.api.dto.riot.RiotMatchDetailResponse;
import io.github.opgg.music_ward_server.utils.api.dto.riot.RiotSummonerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchServiceImpl implements SearchService {

    private final RiotSummonerClient riotSummonerClient;
    private final RiotMatchClient riotMatchClient;
    private final TagRepository tagRepository;
    private final WardRepository wardRepository;
    private final TrackRepository trackRepository;
    private final CommentRepository commentRepository;
    private final PlaylistRepository playlistRepository;
    private final ChampionRepository championRepository;

    @Value("${api.riot.key}")
    private String riotAPIKey;

    @Override
    public SearchSummonerResponse getRiotSummonerInfo(String summonerName) {
        RiotSummonerResponse riotSummonerResponse = riotSummonerClient.getSummonerNameInfo(riotAPIKey, summonerName);
        String userPuuid = riotSummonerResponse.getPuuid();
        List<String> matchList = riotMatchClient.getMatch(riotAPIKey, userPuuid);
        SearchSummonerResponse searchSummonerResponse = getSearchSummonerResponse(matchList, summonerName, userPuuid);
        return searchSummonerResponse;
    }

    @Override
    public Page<PlaylistMainResponse> findByChampionName(String championName, Pageable pageable) {

        Page<Playlist> playlists = playlistRepository.findByChampionName(championName, championName, pageable);
        return toPlaylistMainResponses(playlists);
    }

    @Override
    public Page<PlaylistMainResponse> findByPlaylistTitle(String title, Pageable pageable) {

        Page<Playlist> playlists = playlistRepository.findByTitleContaining(title, pageable);
        return toPlaylistMainResponses(playlists);
    }

    @Override
    public Page<PlaylistMainResponse> findByTagTitle(String title, Pageable pageable) {

        Page<Playlist> playlists = playlistRepository.findByTagTitle(title, pageable);
        return toPlaylistMainResponses(playlists);
    }

    private SearchSummonerResponse getSearchSummonerResponse(
            List<String> matchList, String summonerName, String userPuuid) {

        List<Boolean> winAry = new ArrayList<>();
        List<String> championNameList = new ArrayList<>();
        for (String match : matchList) {
            RiotMatchDetailResponse riotMatchDetailResponse = riotMatchClient.getMatchDetail(riotAPIKey, match);
            List<String> participants = riotMatchDetailResponse.getMetadata().getParticipants();
            List<RiotMatchDetailResponse.Participants> matchParticipantsList =
                    riotMatchDetailResponse.getInfo().getParticipants();
            Integer index = participants.indexOf(userPuuid);
            winAry.add(matchParticipantsList.get(index).isWin());
            championNameList.add(matchParticipantsList.get(index).getChampionName());
        }
        Map<String, Integer> championMap = getChampionMap(championNameList);
        Optional<Champion> champion = championRepository.findByEnglishName(getFavoriteChampion(championMap));
        String win = getWinningStreak(winAry);
        return new SearchSummonerResponse(summonerName, champion.get().getName(), win);
    }


    private String getFavoriteChampion(Map<String, Integer> ChampionList) {
        int maxValue = Collections.max(ChampionList.values());
        for (Map.Entry<String, Integer> m : ChampionList.entrySet()) {
            if (m.getValue() == maxValue) {
                String champion = m.getKey();
                return champion;
            }
        }
        return null;
    }

    private String getWinningStreak(List<Boolean> winAry) {
        Integer winCount = 0;
        Boolean isWin = true;
        String winType = null;
        for (boolean win : winAry) {
            if (winType != null) {
                if (win == isWin) {
                    winCount += 1;
                } else {
                    break;
                }
            } else {
                if (win) {
                    winType = "연승";
                } else {
                    isWin = win;
                    winType = "연패";
                }
                winCount += 1;
            }
        }
        winType = winCount + winType;
        return winType;
    }

    private Map<String, Integer> getChampionMap(List<String> championNameList) {
        Map<String, Integer> championMap = new HashMap<>();
        for (String str : championNameList) {
            Integer championCount = championMap.get(str);
            if (championCount == null) {
                championMap.put(str, 1);
            } else {
                championMap.put(str, championCount + 1);
            }
        }
        return championMap;
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