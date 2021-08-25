package io.github.opgg.music_ward_server.entity.playlist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByUserId(Long userId);
    Page<Playlist> findByChampionName(String championName, Pageable pageable);
    Optional<Playlist> findByIdAndUserId(Long playlistId, Long userId);
}