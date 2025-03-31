package com.chill_guys.order_service.application.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
public class PageResponseDto<T> {
    private final List<T> content;
    private final int currentPage;
    private final int totalPages;
    private final long totalElements;

    public PageResponseDto(Page<T> page) {
        this.content = page.getContent();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}
