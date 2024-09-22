package com.wszib.userservice.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullIdException extends RuntimeException{
    public NullIdException() {
        super("Id cannot be null!");
    }
}

