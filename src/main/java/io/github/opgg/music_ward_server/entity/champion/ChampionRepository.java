package io.github.opgg.music_ward_server.entity.champion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChampionRepository extends JpaRepository<Champion, Long> {

    Optional<Champion> findByName(String name);

    @Query("select distinct c " +
            "from tbl_champion c " +
            "join fetch c.playlists")
    List<Champion> findAllDistinct();
}