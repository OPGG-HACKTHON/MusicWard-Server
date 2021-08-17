package io.github.opgg.music_ward_server.entity.user;

import io.github.opgg.music_ward_server.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_user")
public class User extends BaseEntity implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 20)
    private String googleEmail;

    @Column(length = 20)
    private String spotifyEmail;

    @Column(length = 10)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Role role;

    @Column(length = 10)
    private String provider;

    @Column(length = 10)
    private String nickName;

    @Column(columnDefinition = "BIT(1)")
    private boolean withdrawal;

    @Builder
    public User(String googleEmail, String spotifyEmail, String name, Role role,
                String provider, String nickName, boolean withdrawal) {
        this.googleEmail = googleEmail;
        this.spotifyEmail = spotifyEmail;
        this.name = name;
        this.role = role;
        this.provider = provider;
        this.nickName = nickName;
        this.withdrawal = withdrawal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}