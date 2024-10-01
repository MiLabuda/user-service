package com.wszib.userservice.domain;

import com.wszib.userservice.domain.error.NullIdException;

public record UserId(String value) {

    public UserId {
        if (value == null || value.strip().isBlank()) {
            throw new NullIdException();
        }
    }

    public static UserId of(final String value) {
        return new UserId(value);
    }

    public static UserId generate() {
        return new UserId(java.util.UUID.randomUUID().toString());
    }

    public String value() {
        return value;
    }
}
