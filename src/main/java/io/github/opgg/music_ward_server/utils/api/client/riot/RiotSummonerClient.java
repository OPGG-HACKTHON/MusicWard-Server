package io.github.opgg.music_ward_server.utils.api.client.riot;

import io.github.opgg.music_ward_server.utils.api.dto.riot.RiotSummonerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
@FeignClient(value = "RiotSummonerClient", url = "https://kr.api.riotgames.com")
public interface RiotSummonerClient {

    @GetMapping("/lol/summoner/v4/summoners/by-name/{summonerName}")
    RiotSummonerResponse getSummonerNameInfo(@RequestHeader("X-Riot-Token") String accessToken,
                                             @PathVariable("summonerName") String summonerName);

}
