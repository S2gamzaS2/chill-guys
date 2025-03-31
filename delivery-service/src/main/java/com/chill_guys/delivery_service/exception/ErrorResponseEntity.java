package com.chill_guys.delivery_service.exception;

import lombok.Builder;
import org.springframework.http.ResponseEntity;

@Builder
public record ErrorResponseEntity(int status, String message) {

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.httpStatus())
                .body(
                        ErrorResponseEntity.builder()
                        .status(errorCode.httpStatus().value())
                        .message(errorCode.message())
                        .build());
    }
}
