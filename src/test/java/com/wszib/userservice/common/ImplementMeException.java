package com.wszib.userservice.common;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImplementMeException extends RuntimeException{
    public ImplementMeException() {
        super("Method not implemented yet!");
    }
}