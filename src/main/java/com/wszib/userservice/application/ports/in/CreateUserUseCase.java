package com.wszib.userservice.application.ports.in;

import com.wszib.userservice.domain.command.CreateUserCommand;

public interface CreateUserUseCase {
    void handle(CreateUserCommand command);

}
