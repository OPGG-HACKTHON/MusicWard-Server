package io.github.opgg.music_ward_server.entity.champion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChampionTest {

    @Test
    @DisplayName("Builder를 활용하여 Champion 객체를 생성하는 테스트 - 성공")
    void createByBuilder() {

        // given
        String name = "가렌";
        String title = "데마시아의 힘";
        String englishName = "Garen";
        String story = "가렌은 불굴의 선봉대를 이끄는 고결하고 자긍심 강한 군인이다. " +
                "선봉대 내에서 인망이 두터울 뿐 아니라 심지어 적에게도 존경을 받지만, " +
                "그가 대대로 데마시아와 그 이상을 수호하는 임무를 맡은 크라운가드 가문의 자손이기 때문은 아니다. " +
                "가렌은 자신의 부모님이 마법사에 살해되어 마법을 매우 혐오하며 마법 저항력을 갖춘 방어구와 거대한 대검으로 무장하고, " +
                "언제라도 마법사에 맞서 정의의 칼바람을 일으킬 준비가 되어 있다.";
        String position = "전사";
        String profileImageUrl = "/images/profile/garen.jpg";
        String imageUrl = "/images/garen.jpg";

        // when
        Champion champion = Champion.builder()
                .name(name)
                .title(title)
                .englishName(englishName)
                .story(story)
                .position(position)
                .profileImageUrl(profileImageUrl)
                .imageUrl(imageUrl)
                .build();

        // then
        Assertions.assertAll(
                () -> assertEquals(name, champion.getName()),
                () -> assertEquals(title, champion.getTitle()),
                () -> assertEquals(englishName, champion.getEnglishName()),
                () -> assertEquals(story, champion.getStory()),
                () -> assertEquals(position, champion.getPosition()),
                () -> assertEquals(profileImageUrl, champion.getProfileImageUrl()),
                () -> assertEquals(imageUrl, champion.getImageUrl())
        );
    }
}