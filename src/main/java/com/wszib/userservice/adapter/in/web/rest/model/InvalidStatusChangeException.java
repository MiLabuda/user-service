package com.wszib.userservice.adapter.in.web.rest.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidStatusChangeException extends RuntimeException {

    public InvalidStatusChangeException() {
        super("Invalid status change, User is already in the requested status");
    }
}
