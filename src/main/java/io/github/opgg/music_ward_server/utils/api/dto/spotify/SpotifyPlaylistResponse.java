package io.github.opgg.music_ward_server.utils.api.dto.spotify;

import io.github.opgg.music_ward_server.dto.track.request.TrackSaveRequest;
import io.github.opgg.music_ward_server.utils.api.dto.PlaylistResponse;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SpotifyPlaylistResponse implements PlaylistResponse {

    private boolean collaborate;
    private String description;
    private ExternalUrls external_urls;
    private Followers followers;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private Owner owner;
    private String primary_color;
    private boolean pub;
    private String snapshot_id;
    private Tracks tracks;

    @Getter
    private static class ExternalUrls {
        private String spotify;
    }

    @Getter
    private static class Followers {
        private String href;
        private String total;
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
        private List<Item> items;

        @Getter
        private static class Item {
            private String added_at;
            private boolean is_local;
            private String primary_color;
            private Track track;
            private VideoThumbnail video_thumbnail;

            @Getter
            private static class Track {
                private Album album;
                private List<Artist> artists;
                private String disc_number;
                private String duration_ms;
                private boolean episode;
                private boolean explicit;
                private ExternalIds external_ids;
                private ExternalUrls external_urls;
                private String href;
                private String id;
                private boolean is_local;
                private boolean is_playable;
                private String name;
                private String popularity;
                private String preview_url;
                private boolean track;
                private String track_number;
                private String type;
                private String uri;

                @Getter
                private static class Album {
                    private String album_type;
                    private List<Artist> artists;
                    private ExternalUrls external_urls;
                    private String href;
                    private String id;
                    private List<Image> images;
                    private String name;
                    private String release_date;
                    private String release_date_precision;
                    private String total_tracks;
                    private String type;
                    private String uri;

                    @Getter
                    private static class Artist {
                        private ExternalUrls external_urls;
                        private String href;
                        private String id;
                        private String name;
                        private String type;
                        private String uri;

                        @Getter
                        private static class ExternalUrls {
                            private String spotify;
                        }
                    }

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
                }

                @Getter
                private static class Artist {
                    private ExternalUrls external_urls;
                    private String href;
                    private String id;
                    private String name;
                    private String type;
                    private String uri;

                    @Getter
                    private static class ExternalUrls {
                        private String spotify;
                    }
                }

                @Getter
                private static class ExternalIds {
                    private String isrc;
                }

                @Getter
                private static class ExternalUrls {
                    private String spotify;
                }
            }

            @Getter
            private static class VideoThumbnail {
                private String url;
            }
        }
    }

    @Override
    public io.github.opgg.music_ward_server.entity.playlist.Image getImage() {

        if (images.size() != 0) {
            for (Image image : images) {
                if (image.getWidth().equals("640") && image.getHeight().equals("640")) {
                    return new io.github.opgg.music_ward_server.entity.playlist.Image(
                            image.getUrl(), image.getWidth(), image.getHeight());
                }
            }
        }

        return null;
    }

    @Override
    public List<TrackSaveRequest> getTrackSaveRequests() {

        List<TrackSaveRequest> trackSaveRequests = new ArrayList<>();

        for (Tracks.Item item : tracks.getItems()) {

            io.github.opgg.music_ward_server.entity.playlist.Image trackImage = null;
            for (Tracks.Item.Track.Album.Image image : item.getTrack().getAlbum().getImages()) {
                if (image.getWidth().equals("640") && image.getHeight().equals("640")) {
                    trackImage = new io.github.opgg.music_ward_server.entity.playlist.Image(
                            image.getUrl(), image.getWidth(), image.getHeight()
                    );
                }
            }

            StringBuilder artists = new StringBuilder();
            for (Tracks.Item.Track.Artist artist : item.getTrack().getArtists()) {
                artists.append(artist.getName()).append(", ");
            }

            TrackSaveRequest track = TrackSaveRequest.builder()
                    .originalId(item.getTrack().getId())
                    .title(item.getTrack().getName())
                    .previewUrl(item.getTrack().getPreview_url())
                    .image(trackImage)
                    .artists(artists.substring(0, artists.length() - 2))
                    .build();

            trackSaveRequests.add(track);
        }

        return trackSaveRequests;
    }
}