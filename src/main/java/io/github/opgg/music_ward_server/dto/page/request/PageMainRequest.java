package io.github.opgg.music_ward_server.dto.page.request;

import io.github.opgg.music_ward_server.exception.UnsupportedSortTypeException;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
public class PageMainRequest {

    private static final int DEFAULT_SIZE = 5;
    private static final int MAX_SIZE = 50;

    private static final String CREATED_DATE = "created_date";
    private static final String VIEW = "view";

    private int page;
    private int size;
    private String sort;

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public void setSort(String sort) {
        if (sort == null) {
            sort = CREATED_DATE;
        }
        this.sort = sort;
    }

    public PageRequest toPageRequest() {
        if (this.sort.equals(CREATED_DATE)) {
            return PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        } else if (this.sort.equals(VIEW)) {
            return PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate", "view");
        } else {
            throw new UnsupportedSortTypeException();
        }
    }
}