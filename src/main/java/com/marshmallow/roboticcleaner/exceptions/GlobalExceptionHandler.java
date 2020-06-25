package com.marshmallow.roboticcleaner.exceptions;

import com.marshmallow.roboticcleaner.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDirectionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDirectionException(InvalidDirectionException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(InvalidPositionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPositionException(InvalidPositionException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        StringBuilder builder = new StringBuilder();

        for (FieldError field : fieldErrors) {
            String message = field.getDefaultMessage();
            if (message.isEmpty())
                message = field.getRejectedValue().toString();

            builder.append(" ")
                    .append(field.getField())
                    .append(" ")
                    .append(message)
                    .append(",");
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .message(builder.toString())
                        .build());
    }
}
