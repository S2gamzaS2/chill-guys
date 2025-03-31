package com.chill_guys.delivery_service.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException e) {

        ErrorCode errorCode = e.getErrorCode();

        return ErrorResponseEntity.toResponseEntity(errorCode);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseEntity> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponseEntity.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(e.getBindingResult()
                                        .getAllErrors()
                                        .stream()
                                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                        .collect(Collectors.joining("\n"))
                                )
                                .build()
                );
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseEntity> handleAccessDeniedException(AccessDeniedException e) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        ErrorResponseEntity.builder()
                                .status(HttpStatus.FORBIDDEN.value())
                                .message("권한이 없습니다.")
                                .build()
                );
    }
}
