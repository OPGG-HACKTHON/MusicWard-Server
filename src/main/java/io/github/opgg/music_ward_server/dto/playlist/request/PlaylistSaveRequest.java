package io.github.opgg.music_ward_server.dto.playlist.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.opgg.music_ward_server.entity.champion.Champion;
import io.github.opgg.music_ward_server.entity.playlist.Image;
import io.github.opgg.music_ward_server.entity.playlist.Playlist;
import io.github.opgg.music_ward_server.entity.playlist.Provider;
import io.github.opgg.music_ward_server.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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

    @NotBlank(message = "champion name이 null과 공백을 허용하지 않습니다.")
    private String championName;

    @NotNull(message = "tags는 null을 허용하지 않습니다.")
    private List<String> tags;

    @Builder
    public PlaylistSaveRequest(String originalId, Provider provider, String title,
                               String description, String championName, List<String> tags) {
        this.originalId = originalId;
        this.provider = provider;
        this.title = title;
        this.description = description;
        this.championName = championName;
        this.tags = tags;
    }

    public Playlist toEntity(Image image, User user, Champion champion) {
        return Playlist.builder()
                .originalId(originalId)
                .provider(provider)
                .title(title)
                .description(description)
                .image(image)
                .externalUrl(getExternalUrl())
                .user(user)
                .champion(champion)
                .build();
    }

    private String getExternalUrl() {
        return "https://music.youtube.com/playlist?list=" + this.originalId;
    }
}