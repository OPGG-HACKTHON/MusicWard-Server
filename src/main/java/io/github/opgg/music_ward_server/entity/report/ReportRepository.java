package io.github.opgg.music_ward_server.entity.report;

import io.github.opgg.music_ward_server.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    void deleteByUser(User user);
    void deleteByPlaylistId(Long playlistId);
}