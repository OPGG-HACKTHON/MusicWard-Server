package io.github.opgg.music_ward_server.dto.playlist;

import io.github.opgg.music_ward_server.entity.playlist.Provider;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
public class PlaylistSaveRequest {

    @NotBlank(message = "original id은 null과 공백을 허용하지 않습니다.")
    private String originalId;

    @NotNull(message = "provider은 null을 허용하지 않습니다.")
    private Provider provider;

    @NotBlank(message = "title은 null과 공백을 허용하지 않습니다.")
    @Size(max = 30, message = "title은 30자를 넘을 수 없습니다.")
    private String title;

    @NotNull(message = "descricption이 null을 허용하지 않습니다.")
    @Size(max = 200, message = "description은 200자를 넘을 수 없습니다.")
    private String description;

    @NotBlank(message = "champion이 null과 공백을 허용하지 않습니다.")
    private String champion;

    @NotNull(message = "tags는 null을 허용하지 않습니다.")
    private List<String> tags;

    @Builder
    public PlaylistSaveRequest(String originalId, Provider provider, String title,
                               String description, String champion, List<String> tags) {
        this.originalId = originalId;
        this.provider = provider;
        this.title = title;
        this.description = description;
        this.champion = champion;
        this.tags = tags;
    }
}