package com.wszib.userservice.adapter.out.auth.keycloak.model;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class KeycloakSynchronizationException extends RuntimeException {

    public KeycloakSynchronizationException(String message) {
        super(message);
    }
    public KeycloakSynchronizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
