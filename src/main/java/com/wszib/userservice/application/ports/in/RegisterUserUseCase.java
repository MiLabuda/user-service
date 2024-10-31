package com.wszib.userservice.application.ports.in;

import com.wszib.userservice.domain.command.RegisterUserCommand;

public interface RegisterUserUseCase {
    void handle(RegisterUserCommand command);

}
