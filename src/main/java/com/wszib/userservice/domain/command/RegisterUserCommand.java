package com.wszib.userservice.domain.command;

import com.wszib.userservice.domain.*;
import com.wszib.userservice.infrastructure.command.Command;
import com.wszib.userservice.infrastructure.command.CommandId;

import java.time.Instant;

public record RegisterUserCommand(
        CommandId id,
        Instant registeredAt,
        UserId userId,
        FirstName firstName,
        LastName lastName,
        Email email,
        Password password,
        RoleName roleName,
        Address invoiceAddress,
        Address deliveryAddress
) implements Command {

    public static class Builder {
        private UserId userId;
        private FirstName firstName;
        private LastName lastName;
        private Email email;
        private Password password;
        private RoleName roleName;
        private Address invoiceAddress;
        private Address deliveryAddress;

        public Builder userId(UserId userId) {
            this.userId = userId;
            return this;
        }

        public Builder firstName(FirstName firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder lastName(LastName lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder email(Email email) {
            this.email = email;
            return this;
        }
        public Builder password(Password password) {
            this.password = password;
            return this;
        }
        public Builder roleName(RoleName roleName) {
            this.roleName = roleName;
            return this;
        }
        public Builder invoiceAddress(Address invoiceAddress) {
            this.invoiceAddress = invoiceAddress;
            return this;
        }
        public Builder deliveryAddress(Address deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public RegisterUserCommand build() {
            return new RegisterUserCommand(
                    CommandId.generate(),
                    Command.now(),
                    userId,
                    firstName,
                    lastName,
                    email,
                    password,
                    roleName,
                    invoiceAddress,
                    deliveryAddress
            );
        }
    }
}
