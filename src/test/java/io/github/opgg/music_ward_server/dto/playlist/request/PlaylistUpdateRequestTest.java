package io.github.opgg.music_ward_server.dto.playlist.request;

import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistUpdateRequestTest {

    static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("dto Blank 유무를 체크하는 테스트")
    void validateNotBlank() {

        // given
        PlaylistUpdateRequest request = PlaylistUpdateRequest.builder()
                .title(" ")
                .description("description은 공백을 허용합니다.")
                .championName(" ")
                .tags(new ArrayList<>(Arrays.asList(new String[]{"티모", "매드무비"})))
                .build();

        // when
        Set<ConstraintViolation<PlaylistUpdateRequest>> constraintViolations = validator.validate(request);

        // then
        assertEquals(2, constraintViolations.size());
    }

    @Test
    @DisplayName("dto null 유무를 체크하는 테스트")
    void validateNotNull() {

        // given
        PlaylistUpdateRequest request = PlaylistUpdateRequest.builder()
                .title(null)
                .description(null)
                .championName(null)
                .tags(null)
                .build();

        // when
        Set<ConstraintViolation<PlaylistUpdateRequest>> constraintViolations = validator.validate(request);

        // then
        assertEquals(4, constraintViolations.size());
    }

    @Test
    @DisplayName("dto size 유무를 체크하는 테스트")
    void validateSize() {

        // given
        StringBuilder title = new StringBuilder();
        for (int i = 0; i < 31; i++) {
            title.append(i);
        }

        StringBuilder description = new StringBuilder();
        for (int i = 0; i < 201; i++) {
            description.append(i);
        }

        PlaylistUpdateRequest request = PlaylistUpdateRequest.builder()
                .title(title.toString())
                .description(description.toString())
                .championName("티모")
                .tags(new ArrayList<>(Arrays.asList(new String[]{"티모", "매드무비"})))
                .build();

        // when
        Set<ConstraintViolation<PlaylistUpdateRequest>> constraintViolations = validator.validate(request);

        // then
        assertEquals(2, constraintViolations.size());
    }

    @Test
    @DisplayName("toEntity 메소드 테스트")
    void toEntity() {

        // given
        PlaylistUpdateRequest request = PlaylistUpdateRequest.builder()
                .title("test title")
                .description("test description")
                .championName("티모")
                .tags(new ArrayList<>(Arrays.asList(new String[]{"티모", "매드무비"})))
                .build();

        Champion champion = Champion.builder()
                .name("티모")
                .build();

        // when
        Playlist playlist = request.toEntity(champion);

        // then
        assertAll(
                () -> assertEquals(request.getTitle(), playlist.getTitle()),
                () -> assertEquals(request.getDescription(), playlist.getDescription()),
                () -> assertEquals(request.getChampionName(), playlist.getChampion().getName())
        );
    }
}