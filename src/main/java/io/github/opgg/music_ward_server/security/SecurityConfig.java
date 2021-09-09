package io.github.opgg.music_ward_server.security;

import io.github.opgg.music_ward_server.error.ExceptionHandlerFilter;
import io.github.opgg.music_ward_server.security.jwt.FilterConfigure;
import io.github.opgg.music_ward_server.security.jwt.JwtTokenProvider;
import io.github.opgg.music_ward_server.security.logging.RequestLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final RequestLogger requestLogger;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/swagger-ui.html/**", "/swagger-resources/**").permitAll()
                .antMatchers("/v3/**", "/swagger-ui/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/auth/google").permitAll()
                .antMatchers(HttpMethod.POST, "/users/auth/google").permitAll()
                .antMatchers(HttpMethod.GET, "/users/auth/spotify").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/auth/spotify").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/championlist").permitAll()
                .antMatchers(HttpMethod.GET, "/champion/**").permitAll()
                .antMatchers(HttpMethod.GET, "/playlists/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/**/playlists").permitAll()
                .antMatchers(HttpMethod.GET, "/ranking/**").permitAll()
                .antMatchers(HttpMethod.GET, "/search/**").permitAll()
                .anyRequest().authenticated()
                .and().apply(new FilterConfigure(jwtTokenProvider, exceptionHandlerFilter, requestLogger));
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
				.allowedOrigins("http://localhost:3000", "https://site.music-ward.com");
	}

}
