package io.github.opgg.music_ward_server.entity.playlist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByUserId(Long userId);
    Optional<Playlist> findByIdAndUserId(Long playlistId, Long userId);
    void deleteByIdAndUserId(Long playlistId, Long userId);

    @Query("select p " +
            "from tbl_playlist p " +
            "order by p.createdDate DESC ")
    List<Playlist> findAllOrderByCreatedDate();

    @EntityGraph(attributePaths = {"champion"})
    @Query("select p " +
            "from tbl_playlist p " +
            "join p.champion c " +
            "where c.name like %:championName% or c.englishName like %:englishName% ")
    Page<Playlist> findByChampionName(@Param("championName") String championName,
                                      @Param("englishName") String englishName, Pageable pageable);

    @EntityGraph(attributePaths = {"champion"})
    @Query("select p " +
            "from tbl_playlist p " +
            "join p.champion c " +
            "where p.title like %:title% ")
    Page<Playlist> findByTitleContaining(@Param("title") String title, Pageable pageable);

    @EntityGraph(attributePaths = {"champion"})
    @Query("select distinct p " +
            "from tbl_playlist p " +
            "join p.tags t " +
            "where t.title like %:title% ")
    Page<Playlist> findByTagTitle(@Param("title") String title, Pageable pageable);

    @EntityGraph(attributePaths = {"champion"})
    @Query("select p " +
            "from tbl_playlist p " +
            "join p.wards w " +
            "where w.user.id = :userId ")
    Page<Playlist> findByWardUserId(@Param("userId") Long userId, Pageable pageable);
}