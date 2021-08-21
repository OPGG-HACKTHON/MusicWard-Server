package io.github.opgg.music_ward_server.dto.champion.response;

import lombok.*;

@Getter
public class ChampionDataDTO<T> {

    private T data;

    public ChampionDataDTO(final T data) {
        this.data = data;
    }

}