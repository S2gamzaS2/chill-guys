package com.chill_guys.delivery_service.application.dto.response;

import java.util.List;

public class PageResponseDto<T> {
    private List<T> result;
    private Long total;
}
