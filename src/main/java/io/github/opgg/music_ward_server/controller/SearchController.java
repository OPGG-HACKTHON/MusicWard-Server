package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.controller.response.CommonResponse;
import io.github.opgg.music_ward_server.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/search")
@RequiredArgsConstructor
@RestController
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/summoner")
    public ResponseEntity<CommonResponse> getSearchSummonerResponse(@RequestParam(value = "summoner_name") String summonerName) {
        return new ResponseEntity<>(new CommonResponse(searchService.getRiotSummonerInfo(summonerName)), HttpStatus.OK);
    }


}
