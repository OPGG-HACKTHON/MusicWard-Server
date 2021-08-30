package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.controller.response.CommonResponse;
import io.github.opgg.music_ward_server.dto.user.request.ModifyNicknameRequest;
import io.github.opgg.music_ward_server.dto.user.request.RefreshTokenRequest;
import io.github.opgg.music_ward_server.dto.user.response.LinkResponse;
import io.github.opgg.music_ward_server.dto.user.response.TokenResponse;
import io.github.opgg.music_ward_server.dto.user.response.UserInfoResponse;
import io.github.opgg.music_ward_server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/auth/google")
    public ResponseEntity<CommonResponse<LinkResponse>> getGoogleLink() {
        return new ResponseEntity<>(
                new CommonResponse<>(userService.getGoogleLink()), HttpStatus.OK);
    }

    @GetMapping("/auth/spotify")
    public ResponseEntity<CommonResponse<LinkResponse>> getSpotifyLink() {
        return new ResponseEntity<>(
                new CommonResponse<>(userService.getSpotifyLink()), HttpStatus.OK);
    }

    @PostMapping("/auth/google")
    public ResponseEntity<CommonResponse<TokenResponse>> getTokenByGoogleCode(@RequestParam("code") String code) {
        return new ResponseEntity<>(
                new CommonResponse<>(userService.getTokenByGoogleCode(code)), HttpStatus.CREATED);
    }

    @PostMapping("/auth/spotify")
    public ResponseEntity<CommonResponse<TokenResponse>> getTokenBySpotifyCodeWithJwt(@RequestParam("code") String code) {
        return new ResponseEntity<>(
                new CommonResponse<>(userService.getTokenBySpotifyCodeWithJwt(code)), HttpStatus.CREATED);
    }

    @PutMapping("/auth/spotify")
    public ResponseEntity<CommonResponse<TokenResponse>> getTokenBySpotifyCode(@RequestParam("code") String code) {
        return new ResponseEntity<>(
                new CommonResponse<>(userService.getTokenBySpotifyCode(code)), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<CommonResponse<UserInfoResponse>> getUserInformation() {
        return new ResponseEntity<>(
                new CommonResponse<>(userService.getUserInfo()), HttpStatus.OK);
    }

    @PutMapping("/auth")
    public ResponseEntity<CommonResponse<TokenResponse>> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return new ResponseEntity<>(
                new CommonResponse<>(userService.refreshToken(request)), HttpStatus.OK);
    }
  
    @DeleteMapping("/withdrawal")
    public ResponseEntity withdrawalUser() {
        userService.withdrawalUser();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/nickname")
    public ResponseEntity modifyNickname(@RequestBody @Valid ModifyNicknameRequest request) {
        userService.modifyNickname(request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
