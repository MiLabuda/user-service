package com.wszib.userservice.application.usecase;

import com.wszib.userservice.domain.command.CreateUserCommand;
import com.wszib.userservice.domain.User;

public interface CreateUserUseCase {
    void handle(CreateUserCommand command);

}
