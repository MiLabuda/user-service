package com.wszib.userservice.domain;

public record RoleName(String value) {

    private static final int MAX_LENGTH = 25;

    public RoleName(String value) {
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("RoleName cannot be blank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("RoleName cannot be longer than %d characters", MAX_LENGTH));
        }
        this.value = value;
    }

    public static RoleName of(final String value) {
        return new RoleName(value);
    }
}
