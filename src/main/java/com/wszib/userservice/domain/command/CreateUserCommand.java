package com.wszib.userservice.domain.command;

import com.wszib.userservice.domain.*;
import com.wszib.userservice.infrastructure.common.command.Command;

public record CreateUserCommand(
        FirstName firstName,
        LastName lastName,
        Email email,
        Password password,
        RoleName roleName,
        Address invoiceAddress,
        Address deliveryAddress
) implements Command {
}
