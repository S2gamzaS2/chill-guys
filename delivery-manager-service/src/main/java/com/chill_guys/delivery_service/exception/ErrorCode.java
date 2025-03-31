package com.chill_guys.delivery_service.exception;

import org.springframework.http.HttpStatusCode;

public interface ErrorCode {

    HttpStatusCode httpStatus();

    String message();
}
