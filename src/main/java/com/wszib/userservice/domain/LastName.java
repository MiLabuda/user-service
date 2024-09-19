package com.wszib.userservice.domain;

public record LastName(String value) {

    private static final int MAX_LENGTH = 100;

    public LastName(String value) {
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("LastName cannot be blank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("LastName cannot be longer than %d characters", MAX_LENGTH));
        }
        this.value = value;
    }

    public static LastName of(final String value) {
        return new LastName(value);
    }
}
