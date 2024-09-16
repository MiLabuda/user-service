package com.wszib.userservice.domain;

record LastName(String value) {

    private static final int MAX_LENGTH = 100;

    LastName(String value) {
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("LastName cannot be blank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("LastName cannot be longer than %d characters", MAX_LENGTH));
        }
        this.value = value;
    }
}
