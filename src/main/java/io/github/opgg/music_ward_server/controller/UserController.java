package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.dto.user.response.LinkResponse;
import io.github.opgg.music_ward_server.dto.user.response.TokenResponse;
import io.github.opgg.music_ward_server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/auth/google")
    public LinkResponse getGoogleLink() {
        return userService.getGoogleLink();
    }

    @GetMapping("/auth/spotify")
    public LinkResponse getSpotifyLink() {
        return userService.getSpotifyLink();
    }

    @GetMapping("/auth/google/callback")
    public TokenResponse getGoogleTokenByCode(@RequestParam("code") String code) {
        return userService.getGoogleTokenByCode(code);
    }

    @GetMapping("/auth/spotify/callback")
    public TokenResponse getSpotifyTokenByCode(@RequestParam("code") String code) {
        return userService.getSpotifyTokenByCode(code);
    }

}
