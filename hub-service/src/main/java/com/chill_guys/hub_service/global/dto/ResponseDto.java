package com.chill_guys.hub_service.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseDto<T> {

  private static final String SUCCESS = "SUCCESS";

  private String message;
  private T data;

  public static <T> ResponseDto<T> success(T data) {
    return new ResponseDto<>(SUCCESS, data);
  }

  public static <T> ResponseDto<T> success() {
    return new ResponseDto<>(SUCCESS, null);
  }

  // 에러 핸들러에 의해서 호출되어 사용되는 메서드
  public static <T> ResponseDto<T> exception(String message, T code) {
    return new ResponseDto<>(message, code);
  }
}