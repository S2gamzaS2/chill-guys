package com.chill_guys.hub_service.global.utils;

import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PaginationUtils {

  public static <T> Page<T> paginateList(List<T> list, Pageable pageable) {
    int start = (int) pageable.getOffset();
    int end = Math.min(start + pageable.getPageSize(), list.size());
    List<T> subList = start > list.size() ? Collections.emptyList() : list.subList(start, end);
    return new PageImpl<>(subList, pageable, list.size());
  }
}
