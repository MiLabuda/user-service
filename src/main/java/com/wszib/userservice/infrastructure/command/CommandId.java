package com.wszib.userservice.infrastructure.command;

import java.util.UUID;

public record CommandId(UUID value) {

    public CommandId {
        if (value == null) {
            throw new IllegalArgumentException("CommandId cannot be null");
        }
    }

    public static CommandId from(String value) {
        return new CommandId(UUID.fromString(value));
    }

    public static CommandId generate() {
        return new CommandId(UUID.randomUUID());
    }
}
