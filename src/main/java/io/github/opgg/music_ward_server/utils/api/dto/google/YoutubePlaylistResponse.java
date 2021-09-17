package io.github.opgg.music_ward_server.utils.api.dto.google;

import io.github.opgg.music_ward_server.dto.track.request.TrackSaveRequest;
import io.github.opgg.music_ward_server.entity.playlist.Image;
import io.github.opgg.music_ward_server.utils.api.dto.PlaylistResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class YoutubePlaylistResponse implements PlaylistResponse {

    private String kind;
    private String etag;
    private List<Item> items;

    @Getter
    private static class Item {
        private String kind;
        private String etag;
        private String id;
        private Snippet snippet;
        private ContentDetails contentDetails;
        private Status status;

        @Getter
        private static class Snippet {
            private String publishedAt;
            private String channelId;
            private String title;
            private String description;
            private Map<String, Thumbnail> thumbnails;
            private String channelTitle;
            private String playlistId;
            private String position;
            private ResourceId resourceId;
            private String videoOwnerChannelTitle;
            private String videoOwnerChannelId;

            @Getter
            private static class Thumbnail {
                private String url;
                private String width;
                private String height;
            }

            @Getter
            private static class ResourceId {
                private String kind;
                private String videoId;
            }
        }

        @Getter
        private static class ContentDetails {
            private String videoId;
            private String videoPublishedAt;
        }

        @Getter
        private static class Status {
            private String privacyStatus;
        }
    }

    @Override
    public Image getImage() {
        if (items.size() != 0) {
            Item.Snippet.Thumbnail thumbnail;
            Map<String, Item.Snippet.Thumbnail> thumbnails = items.get(0).getSnippet().getThumbnails();
            if (thumbnails.containsKey("maxres")) {
                thumbnail = thumbnails.get("maxres");
            } else {
                thumbnail = thumbnails.get("default");
            }

            return new Image(thumbnail.getUrl(), thumbnail.getWidth(), thumbnail.getHeight());
        }

        return null;
    }

    @Override
    public List<TrackSaveRequest> getTrackSaveRequests() {

        List<TrackSaveRequest> trackSaveRequests = new ArrayList<>();

        for (Item item : items) {

            Item.Snippet.Thumbnail thumbnail = item.getSnippet().getThumbnails().get("maxres");
            Image image;
            if (thumbnail != null) {
                image = new Image(thumbnail.getUrl(), thumbnail.getWidth(), thumbnail.getHeight());
            } else {
                image = null;
            }

            TrackSaveRequest trackSaveRequest = TrackSaveRequest.builder()
                    .originalId(item.id)
                    .title(item.getSnippet().getTitle())
                    .previewUrl("")
                    .image(image)
                    .artists(item.getSnippet().getVideoOwnerChannelTitle())
                    .build();

            trackSaveRequests.add(trackSaveRequest);
        }

        return trackSaveRequests;
    }
}