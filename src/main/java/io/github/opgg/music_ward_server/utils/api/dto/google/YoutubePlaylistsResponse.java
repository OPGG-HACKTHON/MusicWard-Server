package io.github.opgg.music_ward_server.utils.api.dto.google;

import io.github.opgg.music_ward_server.dto.playlist.response.NonPlaylistsResponse;
import io.github.opgg.music_ward_server.entity.playlist.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class YoutubePlaylistsResponse {

    private String kind;
    private String etag;
    private PageInfo pageInfo;
    private List<Item> items;

    @Getter
    private static class PageInfo {
        private String totalResults;
        private String resultsPerPage;
    }

    @Getter
    private static class Item {
        private String kind;
        private String etag;
        private String id;
        private Snippet snippet;
        private Status status;

        @Getter
        private static class Snippet {
            private String publishedAt;
            private String channelId;
            private String title;
            private String description;
            private Map<String, Thumbnail> thumbnails;
            private String channelTitle;

            @Getter
            private static class Thumbnail {
                private String url;
                private String width;
                private String height;
            }
        }

        @Getter
        private static class Status {
            private String privacyStatus;
        }
    }

    public NonPlaylistsResponse toNonPlaylists() {

        List<NonPlaylistsResponse.Playlist> nonPlaylists = new ArrayList<>();

        for (Item item : items) {

            if (item.getStatus().privacyStatus.equals("private")) {
                continue;
            }

            if (item.getSnippet() != null
                    && !item.getSnippet().getThumbnails().isEmpty()) {

                Item.Snippet.Thumbnail thumbnail;
                if (item.getSnippet().getThumbnails().containsKey("maxres")) {
                    thumbnail = item.getSnippet().getThumbnails().get("maxres");
                } else {
                    thumbnail = item.getSnippet().getThumbnails().get("default");
                }

                NonPlaylistsResponse.Playlist.Image image = NonPlaylistsResponse.Playlist.Image.builder()
                        .url(thumbnail.getUrl())
                        .width(thumbnail.getWidth())
                        .height(thumbnail.getHeight()).build();

                NonPlaylistsResponse.Playlist nonPlaylist = NonPlaylistsResponse.Playlist.builder()
                        .originalId(item.getId())
                        .originalTitle(item.getSnippet().getTitle())
                        .originalDescription(item.getSnippet().getDescription())
                        .image(image)
                        .externalUrl("https://youtube.com/playlist?list=" + item.getId())
                        .build();

                nonPlaylists.add(nonPlaylist);
            }
        }

        return NonPlaylistsResponse.builder()
                .provider(Provider.YOUTUBE)
                .items(nonPlaylists)
                .build();
    }
}