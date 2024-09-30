package com.wszib.userservice.infrastructure.handler;

import com.wszib.userservice.infrastructure.command.Command;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
