package io.github.opgg.music_ward_server.dto.playlist.response;

import io.github.opgg.music_ward_server.entity.playlist.Provider;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class NonPlaylistsResponse {

    private Provider provider;
    private List<Playlist> items;

    @Builder
    public NonPlaylistsResponse(Provider provider, List<Playlist> items) {
        this.provider = provider;
        this.items = items;
    }

    @Getter
    @NoArgsConstructor
    public static class Playlist {
        private String originalId;
        private String originalTitle;
        private String originalDescription;
        private Image image;
        private String externalUrl;

        @Builder
        public Playlist(String originalId, String originalTitle, String originalDescription,
                        Image image, String externalUrl) {
            this.originalId = originalId;
            this.originalTitle = originalTitle;
            this.originalDescription = originalDescription;
            this.image = image;
            this.externalUrl = externalUrl;
        }

        @Getter
        @NoArgsConstructor
        public static class Image {
            private String url;
            private String width;
            private String height;

            @Builder
            public Image(String url, String width, String height) {
                this.url = url;
                this.width = width;
                this.height = height;
            }
        }
    }
}