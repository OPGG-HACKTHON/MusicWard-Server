package io.github.opgg.music_ward_server.entity.ward;

import io.github.opgg.music_ward_server.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {

    List<Ward> findByPlaylistId(Long playlistId);
    Integer countByPlaylistId(Long playlistId);
}