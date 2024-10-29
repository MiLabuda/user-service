package com.wszib.userservice.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    public NotFoundException(String resourceType, String resource) {
        super(resourceType + " with id: " + resource + " not found!");
    }

    public static NotFoundException forUser(String userId) {
        return new NotFoundException("User", userId);
    }

}
