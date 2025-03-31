package com.chill_guys.hub_service.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonExceptionCode implements ExceptionCode {
  INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 매개변수입니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 문제가 발생되었습니다."),
  PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 페이지를 찾을 수 없습니다."),
  INVALID_PAGE_NUMBER(HttpStatus.BAD_REQUEST, "잘못된 페이지 번호입니다. 페이지 번호는 1부터 시작합니다."),
  INVALID_PAGE_SIZE(HttpStatus.BAD_REQUEST, "잘못된 페이지 사이즈입니다. 10, 30, 50 중 하나를 입력해 주세요."),
  INVALID_SORT_BY(HttpStatus.BAD_REQUEST, "잘못된 정렬 기준입니다."),
  ;
  private final HttpStatus httpStatus;
  private final String message;

}
