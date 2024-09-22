package com.wszib.userservice.domain.command;

import com.wszib.userservice.infrastructure.common.command.Command;

public record CreateUserCommand(
        String firstName,
        String lastName,
        String email,
        String password,
        String roleName,
        String addressName,
        String street,
        String city,
        String zipCode,
        String country
) implements Command {
}
