package com.chill_guys.hub_service.global.utils;

import com.chill_guys.hub_service.global.exception.CommonExceptionCode;
import com.chill_guys.hub_service.global.exception.CustomException;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {

  private static final int FIRST_PAGE = 1;
  private static final int DEFAULT_PAGE_SIZE = 10;

  public static Pageable validatePageable(Pageable pageable) {
    validatePageNumber(pageable.getPageNumber());
    validatePageSize(pageable.getPageSize());
    validateSortBy(pageable.getSort());

    int pageNumber = pageable.getPageNumber() - 1;
    if (pageNumber < 0) {
      pageNumber = 0;
    }
    return PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());
  }

  private static void validatePageNumber(int pageNumber) {
    if (pageNumber < FIRST_PAGE) {
      throw new CustomException(CommonExceptionCode.INVALID_PAGE_NUMBER);
    }
  }

  private static boolean validatePageSize(int pageSize) {
    if (!(pageSize == 10 || pageSize == 30 || pageSize == 50)) {
      throw new CustomException(CommonExceptionCode.INVALID_PAGE_SIZE);
    }
    return true;
  }

  private static void validateSortBy(Sort sort) {
    List<String> allowedSortFields = Arrays.asList("createdAt", "updatedAt");

    if (sort == null || sort.isEmpty()) {
      return;
    }

    for (Sort.Order order : sort) {
      if (!allowedSortFields.contains(order.getProperty())) {
        throw new CustomException(CommonExceptionCode.INVALID_SORT_BY);
      }
    }
  }

}
