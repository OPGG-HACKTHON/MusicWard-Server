package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.controller.response.CommonResponse;
import io.github.opgg.music_ward_server.dto.ranking.response.RankingMainResponse;
import io.github.opgg.music_ward_server.exception.UnsupportedRankingTypeException;
import io.github.opgg.music_ward_server.service.ranking.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/")
@RequiredArgsConstructor
@RestController
public class RankingController {

    public static final String CHAMPION = "champion";
    public static final String PLAYLIST = "playlist";

    private final RankingService rankingService;

    @GetMapping("ranking")
    public ResponseEntity<CommonResponse> getRanking(@RequestParam("type") String rankingType) {

        List<RankingMainResponse> ranking;
        if (rankingType.equals(CHAMPION)) {
            ranking = rankingService.getChampionRanking();
        } else if (rankingType.equals(PLAYLIST)) {
            ranking = rankingService.getPlaylistRanking();
        } else {
            throw new UnsupportedRankingTypeException();
        }

        return new ResponseEntity<>(new CommonResponse(ranking), HttpStatus.OK);
    }
}