package io.github.opgg.music_ward_server.utils.api.dto.spotify;

import io.github.opgg.music_ward_server.dto.playlist.response.NonPlaylistsResponse;
import io.github.opgg.music_ward_server.entity.playlist.Provider;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SpotifyPlaylistsResponse {

    private String href;
    private List<Item> items;
    private String limit;
    private String next;
    private String offset;
    private String previous;
    private String total;

    @Getter
    private static class Item {
        private boolean collaborative;
        private String description;
        private ExternalUrls external_urls;
        private String href;
        private String id;
        private List<Image> images;
        private String name;
        private Owner owner;
        private String primary_color;
        private String snapshot_id;
        private boolean pub;
        private Tracks tracks;
        private String type;
        private String uri;

        @Getter
        private static class ExternalUrls {
            private String spotify;
        }

        @Getter
        private static class Image {
            private String height;
            private String url;
            private String width;
        }

        @Getter
        private static class Owner {
            private String display_name;
            private ExternalUrls external_urls;
            private String href;
            private String id;
            private String type;
            private String uri;

            @Getter
            private static class ExternalUrls {
                private String spotify;
            }
        }

        @Getter
        private static class Tracks {
            private String href;
            private String total;
        }
    }

    public NonPlaylistsResponse toNonPlaylists() {

        List<NonPlaylistsResponse.Playlist> nonPlaylists = new ArrayList<>();

        for (Item item : items) {

            Item.Image originalImage = item.images.get(0);
            NonPlaylistsResponse.Playlist.Image image = NonPlaylistsResponse.Playlist.Image
                    .builder()
                    .url(originalImage.getUrl())
                    .width(originalImage.getWidth())
                    .height(originalImage.getHeight())
                    .build();

            NonPlaylistsResponse.Playlist nonPlaylist = NonPlaylistsResponse.Playlist.builder()
                    .originalId(item.getId())
                    .originalTitle(item.getName())
                    .originalDescription(item.getDescription())
                    .image(image)
                    .externalUrl(item.getExternal_urls().getSpotify())
                    .build();

            nonPlaylists.add(nonPlaylist);
        }

        return NonPlaylistsResponse.builder()
                .provider(Provider.SPOTIFY)
                .items(nonPlaylists)
                .build();
    }
}