package com.wszib.userservice.domain.command;

import com.wszib.userservice.domain.*;
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

    public static class Builder {
        private FirstName firstName;
        private LastName lastName;
        private Email email;
        private UserId userId;

        public ChangeUserDetailsCommand.Builder firstName(FirstName firstName) {
            this.firstName = firstName;
            return this;
        }
        public ChangeUserDetailsCommand.Builder lastName(LastName lastName) {
            this.lastName = lastName;
            return this;
        }
        public ChangeUserDetailsCommand.Builder email(Email email) {
            this.email = email;
            return this;
        }
        public ChangeUserDetailsCommand.Builder userId(UserId userId) {
            this.userId = userId;
            return this;
        }


        public ChangeUserDetailsCommand build() {
            return new ChangeUserDetailsCommand(
                    CommandId.generate(),
                    Command.now(),
                    userId,
                    firstName,
                    lastName,
                    email
            );
        }
    }
}
