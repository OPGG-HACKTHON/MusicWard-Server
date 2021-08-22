package io.github.opgg.music_ward_server.entity.track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    List<Track> findByPlaylistId(Long playlistId);
    Integer countByPlaylistId(Long playlistId);
}