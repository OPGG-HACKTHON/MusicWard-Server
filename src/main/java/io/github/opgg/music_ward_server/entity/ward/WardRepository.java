package io.github.opgg.music_ward_server.entity.ward;

import io.github.opgg.music_ward_server.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {
    Integer countByPlaylistId(Long playlistId);
    void deleteByPlaylistId(Long playlistId);
    void deleteByUser(User user);
}