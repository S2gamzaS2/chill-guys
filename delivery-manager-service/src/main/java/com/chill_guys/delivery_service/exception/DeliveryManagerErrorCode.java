package com.chill_guys.delivery_service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeliveryManagerErrorCode implements ErrorCode {

    DELIVERY_MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 배송 담당자를 찾을 수 없습니다."),
    DELIVERY_MANAGER_NOT_AVAILABLE(HttpStatus.NOT_FOUND, "적합한 배송 담당자를 찾을 수 없습니다");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }
    @Override
    public String message() {
        return message;
    }
}
