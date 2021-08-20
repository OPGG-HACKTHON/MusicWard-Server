package io.github.opgg.music_ward_server.service.user;

import io.github.opgg.music_ward_server.dto.user.response.GoogleLinkResponse;
import io.github.opgg.music_ward_server.dto.user.response.TokenResponse;

public interface UserService {
    GoogleLinkResponse getGoogleLink();
    TokenResponse getTokenByCode(String code);
}
