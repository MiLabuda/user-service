package com.wszib.userservice.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserRegistrationFailedException extends RuntimeException {

    public UserRegistrationFailedException(Throwable cause) {
        super("Registration of user failed", cause);
    }
}
