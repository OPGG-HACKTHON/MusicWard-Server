package io.github.opgg.music_ward_server.dto.page.request;

import lombok.Getter;

@Getter
public class PageRequest {

    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 50;

    private int page;
    private int size;

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public PageRequest toPageRequest
}