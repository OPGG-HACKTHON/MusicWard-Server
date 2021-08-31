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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/search")
@RestController
public class SearchController {

    public static final String CHAMPION = "champion";

    private final SearchService searchService;

    @GetMapping("/summoner")
    public ResponseEntity<CommonResponse> getSearchSummonerResponse(
            @RequestParam(value = "summoner_name") String summonerName) {

        return new ResponseEntity<>(new CommonResponse(searchService.getRiotSummonerInfo(summonerName)), HttpStatus.OK);
    }

    @GetMapping("{searchType}")
    public ResponseEntity<? extends CommonResponse> getPlaylist(
            @PathVariable String searchType, @RequestParam String query, PageMainRequest pageMainRequestDto) {

        if (searchType.equals(CHAMPION)) {
            Page<PlaylistMainResponse> page =
                    searchService.findByChampionName(query, pageMainRequestDto.toPageRequest());
            return new ResponseEntity<>(new PageResponse(page.getContent(), new PageInfoResponse(page)), HttpStatus.OK);
        } else {
            throw new UnsupportedSearchTypeException();
        }
    }
}