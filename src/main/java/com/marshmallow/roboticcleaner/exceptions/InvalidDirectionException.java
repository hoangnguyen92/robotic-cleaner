package com.marshmallow.roboticcleaner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Direction!")
public class InvalidDirectionException extends RuntimeException{
}
