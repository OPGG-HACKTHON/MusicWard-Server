package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/auth/google")
    public String getGoogleLink() {
        return userService.getGoogleLink();
    }

}
