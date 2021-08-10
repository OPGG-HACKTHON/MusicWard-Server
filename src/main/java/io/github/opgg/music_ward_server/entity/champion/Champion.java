package io.github.opgg.music_ward_server.entity.champion;

import io.github.opgg.music_ward_server.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_champion")
public class Champion extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "champion_id")
    private Long id;

    private String name;

    private String title;

    private String englishName;

    @Column(columnDefinition = "TEXT")
    private String story;

    private String position;

    private String profileImageUrl;

    private String imageUrl;

    @Builder
    public Champion(String name, String englishName, String story,
                    String position, String profileImageUrl, String imageUrl) {
        this.name = name;
        this.englishName = englishName;
        this.story = story;
        this.position = position;
        this.profileImageUrl = profileImageUrl;
        this.imageUrl = imageUrl;
    }
}