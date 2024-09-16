package com.wszib.userservice.domain;

public record UserId(String value) {

    public UserId {
        if (value == null || value.strip().isBlank()) {
            throw new IllegalArgumentException("UserId cannot be empty!");
        }
    }

    public String value() {
        return value;
    }
}
