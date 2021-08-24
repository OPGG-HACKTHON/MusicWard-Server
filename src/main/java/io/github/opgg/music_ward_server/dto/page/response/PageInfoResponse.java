package io.github.opgg.music_ward_server.dto.page.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageInfoResponse {

    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;

    public PageInfoResponse(Page page) {
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}