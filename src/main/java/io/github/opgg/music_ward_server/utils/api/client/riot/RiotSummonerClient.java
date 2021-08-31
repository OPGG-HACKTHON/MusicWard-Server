package io.github.opgg.music_ward_server.utils.api.client.riot;

import io.github.opgg.music_ward_server.utils.api.dto.riot.RiotSummonerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "RiotSummonerClient", url = "https://kr.api.riotgames.com")
public interface RiotSummonerClient {

    @GetMapping("/lol/summoner/v4/summoners/by-name/{summonerName}")
    RiotSummonerResponse getSummonerNameInfo(@RequestHeader("X-Riot-Token") String accessToken,
                                             @PathVariable("summonerName") String summonerName);
}