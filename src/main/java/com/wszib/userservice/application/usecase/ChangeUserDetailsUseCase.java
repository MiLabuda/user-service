package com.wszib.userservice.application.usecase;

import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;
import com.wszib.userservice.domain.User;

public interface ChangeUserDetailsUseCase {
    void handle(ChangeUserDetailsCommand command);
}
