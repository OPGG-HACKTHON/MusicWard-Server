package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.dto.user.response.GoogleLinkResponse;
import io.github.opgg.music_ward_server.dto.user.response.TokenResponse;
import io.github.opgg.music_ward_server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/auth/google")
    public GoogleLinkResponse getGoogleLink() {
        return userService.getGoogleLink();
    }

    @PostMapping("/auth/google")
    public TokenResponse getTokenByCode(@RequestParam("code") String code) {
        return userService.getTokenByCode(code);
    }

}
