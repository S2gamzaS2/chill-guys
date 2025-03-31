package com.chill_guys.hub_service.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
  private final ExceptionCode exception;

  public CustomException(ExceptionCode e) {
    super(e.getMessage());
    this.exception = e;
  }
}
