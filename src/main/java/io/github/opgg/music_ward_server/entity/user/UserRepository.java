package io.github.opgg.music_ward_server.entity.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByGoogleEmail(String googleEmail);
    Optional<User> findBySpotifyEmail(String spotifyEmail);
}