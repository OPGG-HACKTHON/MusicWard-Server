package io.github.opgg.music_ward_server.utils.api.dto.riot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.opgg.music_ward_server.dto.playlist.response.NonPlaylistsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RiotMatchDetailResponse {
    private MetaData metadata;
    private Info info;

    @Getter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MetaData {
        private List<String> participants;
        private String dataVersion;
        private String matchId;
    }

        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Info {
            private List<Participants> participants;
        }

            @Getter
            @NoArgsConstructor
            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            public static class Participants {
                private String championName;
                private String puuid;
                private String teamPosition;
                private String summonerName;
                private boolean win;
            }

}
