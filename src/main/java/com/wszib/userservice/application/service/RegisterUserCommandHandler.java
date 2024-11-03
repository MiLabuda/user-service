package com.wszib.userservice.application.service;

import com.wszib.userservice.adapter.out.auth.keycloak.model.KeycloakSynchronizationException;
import com.wszib.userservice.application.ports.in.RegisterUserUseCase;
import com.wszib.userservice.application.ports.out.UserRepository;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.domain.UserFactory;
import com.wszib.userservice.domain.command.RegisterUserCommand;
import com.wszib.userservice.domain.error.UserRegistrationFailedException;
import com.wszib.userservice.infrastructure.handler.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RegisterUserCommandHandler implements CommandHandler<RegisterUserCommand>, RegisterUserUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterUserCommandHandler.class);

    private final UserRepository userRepository;
    private final UserFactory userFactory;

    @Override
    public void handle(RegisterUserCommand command) {
        LOGGER.info("Creating user...");

        try {
            User user = userFactory.registerUser(command);
            userRepository.save(user);
            LOGGER.info("User registered and synchronized with Keycloak successfully");
        } catch (KeycloakSynchronizationException e) {
            LOGGER.error("Failed to synchronize User with Keycloak", e);
            throw new UserRegistrationFailedException(e);
        }
    }
}
