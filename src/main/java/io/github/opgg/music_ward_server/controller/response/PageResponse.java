package io.github.opgg.music_ward_server.controller.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.opgg.music_ward_server.dto.page.response.PageInfoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PageResponse<T> extends CommonResponse {

    PageInfoResponse pageInfo;

    public PageResponse(T data, PageInfoResponse pageInfoResponse) {
        super(data);
        this.pageInfo = pageInfoResponse;
    }
}