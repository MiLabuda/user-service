package com.wszib.userservice.domain;

public record UserRoleId(String value) {

    public UserRoleId {
        if (value == null || value.strip().isBlank()) {
            throw new IllegalArgumentException("UserRoleId cannot be empty!");
        }
    }

    public static UserRoleId of(final String value) {
        return new UserRoleId(value);
    }

    public static UserRoleId generate() {
        return new UserRoleId(java.util.UUID.randomUUID().toString());
    }

    public String value() {
        return value;
    }
}
