package io.github.opgg.music_ward_server.entity.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPlaylistId(Long playlistId);
    Integer countByPlaylistId(Long playlistId);
    void deleteByPlaylistId(Long playlistId);
}