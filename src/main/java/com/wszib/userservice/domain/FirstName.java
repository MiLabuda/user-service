package com.wszib.userservice.domain;

public record FirstName(String value) {

    private static final int MAX_LENGTH = 100;

    public FirstName(String value) {
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("FirstName cannot be blank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("FirstName cannot be longer than %d characters", MAX_LENGTH));
        }
        this.value = value;
    }

    public static FirstName of(final String value) {
        return new FirstName(value);
    }
}
