package io.github.opgg.music_ward_server.service.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final String GOOGLE_LOGIN_LINK = "https://accounts.google.com/o/oauth2/v2/auth";

    @Value("${oauth.google.client_id}")
    private String clientId;

    @Value("${oauth.redirect_uri}")
    private String redirectUri;

    @Override
    public String getGoogleLink() {
        return GOOGLE_LOGIN_LINK +
                "?client_id=" + clientId +
                "&scope=https%3A//www.googleapis.com/auth/userinfo.email" +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri;
    }

}
