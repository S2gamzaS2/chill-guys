package com.chill_guys.delivery_service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeliveryErrorCode implements ErrorCode {

    DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 배송을 찾을 수 없습니다."),
    DELIVERY_IN_START(HttpStatus.CONFLICT, "이미 배송이 시작되어 수정할 수 없습니다."),

    DELIVERY_ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 배송 경로 기록을 찾을 수 없습니다.");

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
