package io.github.opgg.music_ward_server.dto.playlist.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlaylistReportRequest {

    @NotNull(message = "plyalist id은 null을 허용하지 않습니다.")
    private Long playlistId;

}
