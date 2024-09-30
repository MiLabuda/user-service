package com.wszib.userservice.common;


import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@DisplayName("Exception thrown when a method is not implemented yet")
class ImplementMeException extends RuntimeException{
    ImplementMeException() {
        super("Method not implemented yet!");
    }
}