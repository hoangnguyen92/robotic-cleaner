package com.marshmallow.roboticcleaner.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class InvalidDirectionException extends RuntimeException{
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public InvalidDirectionException(String message) {
        super(message);
    }
}
