package io.github.opgg.music_ward_server.utils.security;

import io.github.opgg.music_ward_server.entity.user.User;
import io.github.opgg.music_ward_server.exception.CredentialsNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {

    public static Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || authentication.getPrincipal() == null
                || !(authentication instanceof UserDetails)) {
            throw new CredentialsNotFoundException();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;

        return user.getId();
    }
}