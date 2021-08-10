package io.github.opgg.music_ward_server.entity.track;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {
}