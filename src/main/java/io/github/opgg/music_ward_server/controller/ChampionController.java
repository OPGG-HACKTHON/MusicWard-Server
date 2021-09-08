package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.controller.response.CommonResponse;
import io.github.opgg.music_ward_server.service.champion.ChampionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChampionController {

    private final ChampionService championService;

    @GetMapping("/championlist")
    public ResponseEntity<CommonResponse> getChampoinList(
            @RequestParam(value = "champion_name", required = false) String championName,
            @RequestParam(value = "positions", required = false) String positions,
            @SortDefault(sort = "name", direction = Sort.Direction.ASC) Sort sort) {

        return new ResponseEntity<>(
                new CommonResponse(championService.getChampionList(positions, championName, sort)), HttpStatus.OK
        );
    }

    @GetMapping("/champion/{championId}")
    public ResponseEntity<CommonResponse> getChampionDetail(@PathVariable("championId") Long championId) {

        return new ResponseEntity<>(new CommonResponse(championService.getChampionDetail(championId)), HttpStatus.OK);
    }
}