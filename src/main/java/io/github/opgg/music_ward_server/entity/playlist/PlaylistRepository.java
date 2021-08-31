package io.github.opgg.music_ward_server.entity.playlist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByUserId(Long userId);
    Page<Playlist> findByChampionName(String championName, Pageable pageable);
    Optional<Playlist> findByIdAndUserId(Long playlistId, Long userId);

    @Query("select p " +
            "from tbl_playlist p " +
            "order by p.createdDate DESC ")
    List<Playlist> findAllOrderByCreatedDate();

    @Query("select p " +
            "from tbl_playlist p " +
            "join p.champion c " +
            "where c.name like %:championName% ")
    Page<Playlist> findByChampionNameContaining(@Param("championName") String championName, Pageable pageable);

    @Query("select p " +
            "from tbl_playlist p " +
            "join p.champion c " +
            "where p.title like %:title% ")
    Page<Playlist> findByTitleContaining(@Param("title") String title, Pageable pageable);
}