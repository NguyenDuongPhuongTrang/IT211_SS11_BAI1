package com.example.bai1.exception;

import com.example.bai1.model.dto.response.ApiDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiDataResponse<Object>>
    handleValidation(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult()
                .getAllErrors()
                .forEach(error -> {

                    String field =
                            ((FieldError) error).getField();

                    String message =
                            error.getDefaultMessage();

                    errors.put(field, message);
                });

        log.warn("Validation lỗi: {}", errors);

        return new ResponseEntity<>(

                ApiDataResponse.builder()
                        .success(false)
                        .message("Dữ liệu không hợp lệ")
                        .data(null)
                        .error(errors)
                        .status(HttpStatus.BAD_REQUEST)
                        .build(),

                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiDataResponse<Object>>
    handleRuntime(RuntimeException e) {

        log.error("Lỗi hệ thống: {}", e.getMessage(), e);

        return new ResponseEntity<>(

                ApiDataResponse.builder()
                        .success(false)
                        .message("Thao tác thất bại")
                        .data(null)
                        .error(e.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .build(),

                HttpStatus.BAD_REQUEST
        );
    }
}