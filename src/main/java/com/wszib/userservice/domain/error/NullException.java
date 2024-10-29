package com.wszib.userservice.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullException extends RuntimeException{
    public NullException(String resourceType) {
        super(resourceType + "cannot be null!");
    }

    public static NullException forId() {
        return new NullException("ID ");
    }

    public static NullException forFilterCriteria() {
        return new NullException("Filter criteria ");
    }
}

