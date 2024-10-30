package com.wszib.userservice.application.service;

import com.wszib.userservice.adapter.out.auth.keycloak.model.KeycloakSynchronizationException;
import com.wszib.userservice.application.ports.out.KeycloakUserPort;
import com.wszib.userservice.domain.command.CreateUserCommand;
import com.wszib.userservice.application.ports.in.CreateUserUseCase;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.application.ports.out.UserRepository;
import com.wszib.userservice.infrastructure.handler.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CreateUserCommandHandler implements CommandHandler<CreateUserCommand>, CreateUserUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUserCommandHandler.class);

    private final UserRepository userRepository;
    private final KeycloakUserPort keycloakUserPort;

    @Override
    public void handle(CreateUserCommand command) {
        LOGGER.info("Creating user...");
        User user = User.createBy(command);
        userRepository.save(user);
        LOGGER.info("User registered successfully");
        try {
            keycloakUserPort.createUser(user);
            user.markAsSynced();
            userRepository.save(user);
            LOGGER.info("User successfully synchronized with Keycloak");
        } catch (KeycloakSynchronizationException e) {
            user.markAsError();
            userRepository.save(user);
            LOGGER.error("Failed to synchronize User with Keycloak", e);
        }
    }
}
