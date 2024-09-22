package com.wszib.userservice.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullFilterCriteriaException extends RuntimeException{
    public NullFilterCriteriaException() {
        super("Filter criteria cannot be null!");
    }
}
