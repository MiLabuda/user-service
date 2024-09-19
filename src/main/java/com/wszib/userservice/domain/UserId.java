package com.wszib.userservice.domain;

public record UserId(String value) {

    public UserId {
        if (value == null || value.strip().isBlank()) {
            throw new IllegalArgumentException("UserId cannot be empty!");
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
