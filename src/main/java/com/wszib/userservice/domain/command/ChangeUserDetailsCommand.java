package com.wszib.userservice.domain.command;

import com.wszib.userservice.infrastructure.common.command.Command;

public record ChangeUserDetailsCommand(String userId, String firstName, String lastName, String email) implements Command {
}
