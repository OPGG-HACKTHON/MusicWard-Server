package io.github.opgg.music_ward_server.entity.playlist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}