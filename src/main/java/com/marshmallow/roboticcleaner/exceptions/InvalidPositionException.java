package com.marshmallow.roboticcleaner.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class InvalidPositionException extends RuntimeException implements Serializable {
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public InvalidPositionException(String message) {
        super(message);
    }
}
