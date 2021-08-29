package io.github.opgg.music_ward_server.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ModifyNicknameRequest {

    @NotNull(message = "nickname는 Null을 허용하지 않습니다.")
    private String nickname;

}
