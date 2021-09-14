package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.controller.response.CommonResponse;
import io.github.opgg.music_ward_server.controller.response.PageResponse;
import io.github.opgg.music_ward_server.dto.page.request.PageMainRequest;
import io.github.opgg.music_ward_server.dto.page.response.PageInfoResponse;
import io.github.opgg.music_ward_server.dto.playlist.response.PlaylistMainResponse;
import io.github.opgg.music_ward_server.exception.UnsupportedSearchTypeException;
import io.github.opgg.music_ward_server.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/search")
@RestController
public class SearchController {

    // search type
    public static final String CHAMPION = "champion";
    public static final String PLAYLIST = "playlist";
    public static final String TAG = "tag";

    private final SearchService searchService;

    @GetMapping("/summoner")
    public ResponseEntity<CommonResponse> getSearchSummonerResponse(
            @RequestParam(value = "summoner_name") String summonerName) {

        return new ResponseEntity<>(new CommonResponse(searchService.getRiotSummonerInfo(summonerName)), HttpStatus.OK);
    }

    @GetMapping("{searchType}")
    public ResponseEntity<? extends CommonResponse> getPlaylist(
            @PathVariable String searchType, @RequestParam String query, PageMainRequest pageMainRequestDto) {

        PageRequest pageRequest = pageMainRequestDto.toPageRequest();

        Page<PlaylistMainResponse> page;
        if (searchType.equals(CHAMPION)) {
            page = searchService.findByChampionName(query, pageRequest);
        } else if (searchType.equals(PLAYLIST)) {
            page = searchService.findByPlaylistTitle(query, pageRequest);
        } else if (searchType.equals(TAG)) {
            page = searchService.findByTagTitle(query, pageRequest);
        } else {
            throw new UnsupportedSearchTypeException();
        }

        return new ResponseEntity<>(new PageResponse(page.getContent(), new PageInfoResponse(page)), HttpStatus.OK);
    }
}