package com.pcbuilder.build.controller;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class BuildExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> notFound(NoSuchElementException exception) {
        return response(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, Object>> badRequest(Exception exception) {
        String message = exception instanceof MethodArgumentNotValidException validation
                ? validation.getBindingResult().getFieldErrors().stream()
                    .findFirst().map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .orElse("Request validation failed.")
                : exception.getMessage();
        return response(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, Object>> dependencyFailure(FeignException exception) {
        return response(HttpStatus.BAD_GATEWAY,
                "Catalog or compatibility service could not complete the request.");
    }

    private ResponseEntity<Map<String, Object>> response(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        ));
    }
}
