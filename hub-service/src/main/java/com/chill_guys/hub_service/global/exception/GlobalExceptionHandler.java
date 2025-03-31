package com.chill_guys.hub_service.global.exception;

import com.chill_guys.hub_service.global.dto.ResponseDto;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, Object> body = new LinkedHashMap<>(); // 순서 유지

    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", "Bad Request");
    body.put("message", ex.getMessage());  // 예외 메시지 포함

    List<String> errors = ex.getBindingResult().getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

    body.put("message", errors);
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> handleCustomException(CustomException e) {
    ExceptionCode exceptionCode = e.getException();
    return handleExceptionInternal(exceptionCode);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
    log.warn("handleIllegalArgument", e);
    ExceptionCode exceptionCode = CommonExceptionCode.INVALID_PARAMETER;
    return handleExceptionInternal(e.getMessage(), exceptionCode);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleAllException(Exception ex) {
    log.warn("handleAllException", ex);
    ExceptionCode exceptionCode = CommonExceptionCode.INTERNAL_SERVER_ERROR;
    return handleExceptionInternal(ex.getMessage(), exceptionCode);
  }

  private ResponseEntity<?> handleExceptionInternal(ExceptionCode exceptionCode) {
    return ResponseEntity.status(exceptionCode.getHttpStatus())
        .body(makeErrorResponse(exceptionCode));
  }

  private ResponseDto<?> makeErrorResponse(ExceptionCode exceptionCode) {
    return ResponseDto.exception(exceptionCode.getMessage(), exceptionCode);
  }

  private ResponseEntity<?> handleExceptionInternal(String message, ExceptionCode exceptionCode) {
    return ResponseEntity.status(exceptionCode.getHttpStatus())
        .body(makeErrorResponse(message, exceptionCode));
  }

  private ResponseDto<?> makeErrorResponse(String message, ExceptionCode exceptionCode) {
    return ResponseDto.exception(message, exceptionCode);
  }
}
