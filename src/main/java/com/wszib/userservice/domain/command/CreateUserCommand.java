package com.wszib.userservice.domain.command;

import com.wszib.userservice.domain.*;
import com.wszib.userservice.infrastructure.command.Command;
import com.wszib.userservice.infrastructure.command.CommandId;

import java.time.Instant;

public record CreateUserCommand(
        CommandId id,
        Instant registeredAt,
        FirstName firstName,
        LastName lastName,
        Email email,
        Password password,
        RoleName roleName,
        Address invoiceAddress,
        Address deliveryAddress
) implements Command {
}
