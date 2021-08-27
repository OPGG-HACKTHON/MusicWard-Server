package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.controller.response.CommonResponse;
import io.github.opgg.music_ward_server.service.champion.ChampionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RequiredArgsConstructor
@RestController
public class RankingController {

    private final ChampionService championService;

    @GetMapping("ranking/{rankingType}")
    public ResponseEntity<CommonResponse> getRanking(@PathVariable("rankingType") String rankingType) {

        if (rankingType.equals("champion")) {
            return new ResponseEntity<>(new CommonResponse(championService.getRanking()), HttpStatus.OK);
        }

        return new ResponseEntity<>(new CommonResponse(null), HttpStatus.OK);
    }
}