package io.github.opgg.music_ward_server.utils.api.client.riot;

import io.github.opgg.music_ward_server.utils.api.dto.riot.RiotMatchDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value = "RiotMatchClient", url = "https://asia.api.riotgames.com")
public interface RiotMatchClient {

    @GetMapping("/lol/match/v5/matches/by-puuid/{puuid}/ids")
    List<String> getMatch(@RequestHeader("X-Riot-Token") String accessToken,
                          @PathVariable("puuid") String puuid);

    @GetMapping("/lol/match/v5/matches/{matchId}")
    RiotMatchDetailResponse getMatchDetail(@RequestHeader("X-Riot-Token") String accessToken,
                                           @PathVariable("matchId") String matchId);
}