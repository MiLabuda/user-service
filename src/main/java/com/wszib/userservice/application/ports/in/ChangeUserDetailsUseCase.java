package com.wszib.userservice.application.ports.in;

import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;

public interface ChangeUserDetailsUseCase {
    void handle(ChangeUserDetailsCommand command);
}
