package com.wszib.userservice.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String resourceType, String resourceId) {
        super("Access denied for the " + resourceType + " with ID: " + resourceId);
    }

    public static AccessDeniedException forUser(String userId) {
        return new AccessDeniedException("User", userId);
    }
}
