package com.wszib.userservice.domain;

import java.util.regex.Pattern;

public record Email(String value) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public Email {
        var emailValue = value.strip();
        if (emailValue.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty!");
        }
        if (!EMAIL_PATTERN.matcher(emailValue).matches()) {
            throw new IllegalArgumentException("Invalid value format!");
        }
    }

    public static Email of(final String email) {
        return new Email(email);
    }

    public String value() {
        return value;
    }
}
