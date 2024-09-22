package com.wszib.userservice.infrastructure.command;

import java.time.Instant;

public interface Command {

    static Instant now() {
        return Instant.now();
    }

    CommandId id();
}
