package io.github.opgg.music_ward_server.security.jwt;

import io.github.opgg.music_ward_server.security.jwt.auth.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.exp}")
    private Long accessExp;

    @Value("${jwt.refresh.exp}")
    private Long refreshExp;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    private final AuthDetailsService authDetailsService;

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, getSecretKey())
                .setHeaderParam("typ", "JWT")
                .setSubject(email)
                .claim("type", "access")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExp * 1000))
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, getSecretKey())
                .setHeaderParam("typ", "JWT")
                .setSubject(email)
                .claim("type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExp * 1000))
                .compact();
    }

    public boolean isRefreshToken(String token) {
        return getTokenBody(token).get("type").equals("refresh");
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(header);
        if(bearer != null && bearer.length() > 7 && bearer.startsWith(prefix)) {
            return bearer.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        return getTokenBody(token)
                .getExpiration().after(new Date());
    }

    public Authentication authentication(String token) {
        UserDetails userDetails = authDetailsService
                .loadUserByUsername(getTokenSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Claims getTokenBody(String token) {
        return Jwts.parser().setSigningKey(getSecretKey())
                .parseClaimsJws(token).getBody();
    }

    private String getTokenSubject(String token) {
        return getTokenBody(token).getSubject();
    }

    private String getSecretKey() {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

}
