package com.wszib.userservice.domain;

public record UserRoleId(String value) {

    public UserRoleId {
        if (value == null || value.strip().isBlank()) {
            throw new IllegalArgumentException("UserRoleId cannot be empty!");
        }
    }

    public String value() {
        return value;
    }
}
