package com.wszib.userservice.domain;

public record AddressId(String value) {

    public AddressId {
        if (value == null || value.strip().isBlank()) {
            throw new IllegalArgumentException("AddressId cannot be empty!");
        }
    }

    public String value() {
        return value;
    }
}
