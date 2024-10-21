package com.wszib.userservice.application.service;

import com.wszib.userservice.application.ports.out.KeycloakUserPort;
import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;
import com.wszib.userservice.domain.error.NullIdException;
import com.wszib.userservice.application.ports.in.ChangeUserDetailsUseCase;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.domain.UserNotFoundException;
import com.wszib.userservice.application.ports.out.UserRepository;
import com.wszib.userservice.infrastructure.handler.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChangeUserDetailsCommandHandler implements CommandHandler<ChangeUserDetailsCommand>, ChangeUserDetailsUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeUserDetailsCommandHandler.class);

    private final UserRepository userRepository;
    private final KeycloakUserPort keycloakUserPort;


    @Override
    public void handle(ChangeUserDetailsCommand command) {
        LOGGER.info("Changing user details...");
        String userId = command.userId().value();
        if(userId == null) throw new NullIdException();
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User updatedUser = existingUser.changeDetailsBy(command);
        userRepository.changeUserDetails(updatedUser);
        keycloakUserPort.updateUser(updatedUser);
        LOGGER.info("User details changed successfully");
    }
}
