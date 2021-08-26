package io.github.opgg.music_ward_server.entity.playlist;

import io.github.opgg.music_ward_server.exception.UnsupportedProviderException;

public enum Provider {

    YOUTUBE,
    SPOTIFY;

    public static Provider toProvider(String provider) {
        if (provider.equals(Provider.YOUTUBE.name())) {
            return Provider.YOUTUBE;
        } else if (provider.equals(Provider.SPOTIFY.name())) {
            return Provider.SPOTIFY;
        } else {
            throw new UnsupportedProviderException();
        }
    }
}