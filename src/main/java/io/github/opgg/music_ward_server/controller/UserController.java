package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.controller.response.CommonResponse;
import io.github.opgg.music_ward_server.dto.user.response.LinkResponse;
import io.github.opgg.music_ward_server.dto.user.response.TokenResponse;
import io.github.opgg.music_ward_server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/auth/google")
    public CommonResponse<LinkResponse> getGoogleLink() {
        return new CommonResponse<>(userService.getGoogleLink());
    }

    @GetMapping("/auth/spotify")
    public CommonResponse<LinkResponse> getSpotifyLink() {
        return new CommonResponse<>(userService.getSpotifyLink());
    }

    @PostMapping("/auth/google")
    public CommonResponse<TokenResponse> getGoogleTokenByCode(@RequestParam("code") String code) {
        return new CommonResponse<>(userService.getGoogleTokenByCode(code));
    }

    @PostMapping("/auth/spotify")
    public CommonResponse<TokenResponse> getSpotifyTokenByCode(@RequestParam("code") String code) {
        return new CommonResponse<>(userService.getSpotifyTokenByCode(code));
    }

}
