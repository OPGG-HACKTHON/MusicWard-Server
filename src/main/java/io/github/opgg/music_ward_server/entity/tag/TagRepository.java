package io.github.opgg.music_ward_server.entity.tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByPlaylistId(Long playlistId);
    void deleteByPlaylistId(Long playlistId);

    @Query("select t " +
            "from tbl_tag t " +
            "join t.playlist p " +
            "where t.title like %:title% ")
    Page<Tag> findByTitle(@Param("title") String title, Pageable pageable);
}