package com.wszib.userservice.domain;

import com.wszib.userservice.domain.validator.ValidPassword;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

class Password {
    @ValidPassword
    private final String value;

    public Password(String value) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Password>> violations = validator.validateValue(Password.class, "value", value);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations.iterator().next().getMessage());
        }
        this.value = value;
    }
}
