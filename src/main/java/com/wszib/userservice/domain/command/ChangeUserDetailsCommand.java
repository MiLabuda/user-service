package com.wszib.userservice.domain.command;

import com.wszib.userservice.domain.Email;
import com.wszib.userservice.domain.FirstName;
import com.wszib.userservice.domain.LastName;
import com.wszib.userservice.domain.UserId;
import com.wszib.userservice.infrastructure.command.Command;
import com.wszib.userservice.infrastructure.command.CommandId;

import java.time.Instant;

public record ChangeUserDetailsCommand(
        CommandId id,
        Instant registeredAt,
        UserId userId,
        FirstName firstName,
        LastName lastName,
        Email email
) implements Command {
}
