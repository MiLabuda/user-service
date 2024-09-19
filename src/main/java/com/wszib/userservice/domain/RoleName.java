package com.wszib.userservice.domain;

public enum RoleName{
    ADMIN("ADMIN"),
    USER("CUSTOMER");

    private final String value;

    RoleName(String value) {
        this.value = value;
    }

    public static RoleName of(String value) {
        if (value == null) {
            return null;
        }
        for (RoleName roleName : RoleName.values()) {
            if (roleName.value.equalsIgnoreCase(value)) {
                return roleName;
            }
        }
        throw new IllegalArgumentException("RoleName not found: " + value);
    }

    public String value() {
        return value;
    }
}

