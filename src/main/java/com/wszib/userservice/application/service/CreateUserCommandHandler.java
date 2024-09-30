package com.wszib.userservice.application.service;

import com.wszib.userservice.domain.command.CreateUserCommand;
import com.wszib.userservice.application.usecase.CreateUserUseCase;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.domain.UserRepository;
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

    @Override
    public void handle(CreateUserCommand command) {
        LOGGER.info("Creating user...");
        User user = User.createBy(command);
        userRepository.save(user);
        LOGGER.info("User created successfully");
    }
}
