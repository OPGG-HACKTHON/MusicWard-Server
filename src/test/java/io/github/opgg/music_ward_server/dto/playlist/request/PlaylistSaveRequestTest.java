package io.github.opgg.music_ward_server.dto.playlist.request;

import io.github.opgg.music_ward_server.entity.playlist.Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistSaveRequestTest {

    static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("dto Blank 유무를 체크하는 테스트")
    void validateNotBlack() {

        // given
        PlaylistSaveRequest request = PlaylistSaveRequest.builder()
                .originalId(" ")
                .provider("YOUTUBE")
                .title(" ")
                .description("description은 공백을 허용합니다.")
                .championName(" ")
                .tags(new ArrayList<>(Arrays.asList(new String[]{"티모" , "매드무비"})))
                .build();

        // when
        Set<ConstraintViolation<PlaylistSaveRequest>> constraintViolations = validator.validate(request);

        // then
        assertEquals(3, constraintViolations.size());
    }

    @Test
    @DisplayName("dto empty 유무를 체크하는 테스트")
    void validateNotEmpty() {

        // given
        PlaylistSaveRequest request = PlaylistSaveRequest.builder()
                .originalId("")
                .provider("YOUTUBE")
                .title("")
                .description("dereciption은 empty도 허용합니다.")
                .championName("")
                .tags(new ArrayList<>(Arrays.asList(new String[]{"티모" , "매드무비"})))
                .build();

        // when
        Set<ConstraintViolation<PlaylistSaveRequest>> constraintViolations = validator.validate(request);

        // then
        assertEquals(3, constraintViolations.size());
    }

    @Test
    @DisplayName("dto null 유무를 체크하는 테스트")
    void validateNotNull() {

        // given
        PlaylistSaveRequest request = PlaylistSaveRequest.builder()
                .originalId(null)
                .provider(null)
                .title(null)
                .description(null)
                .championName(null)
                .tags(null)
                .build();

        // when
        Set<ConstraintViolation<PlaylistSaveRequest>> constraintViolations = validator.validate(request);

        // then
        assertEquals(6, constraintViolations.size());
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

        PlaylistSaveRequest request = PlaylistSaveRequest.builder()
                .originalId("1234")
                .provider("YOUTUBE")
                .title(title.toString())
                .description(description.toString())
                .championName("티모")
                .tags(new ArrayList<>(Arrays.asList(new String[]{"티모" , "매드무비"})))
                .build();

        // when
        Set<ConstraintViolation<PlaylistSaveRequest>> constraintViolations = validator.validate(request);

        // then
        assertEquals(2, constraintViolations.size());
    }
}