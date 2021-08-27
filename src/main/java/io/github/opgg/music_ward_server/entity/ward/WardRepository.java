package io.github.opgg.music_ward_server.entity.ward;

import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {
    Integer countByPlaylistId(Long playlistId);
    Optional<Ward> findByUserAndPlaylist(User user, Playlist playlist);
    void deleteByPlaylistId(Long playlistId);
    void deleteByUser(User user);
}