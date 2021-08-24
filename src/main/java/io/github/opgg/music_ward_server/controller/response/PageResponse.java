package io.github.opgg.music_ward_server.controller.response;

import io.github.opgg.music_ward_server.dto.page.response.PageInfoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageResponse<T> extends CommonResponse {

    PageInfoResponse pageInfo;

    public PageResponse(T data, PageInfoResponse pageInfoResponse) {
        super(data);
        this.pageInfo = pageInfoResponse;
    }
}