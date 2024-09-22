package com.wszib.userservice.application.service;

import com.wszib.userservice.domain.command.CreateUserCommand;
import com.wszib.userservice.application.usecase.CreateUserUseCase;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CreateUserService implements CreateUserUseCase {

    private static final Logger LOGGER = LogManager.getLogger(CreateUserService.class);

    private final UserRepository userRepository;

    @Override
    public User create(CreateUserCommand command) {
        LOGGER.info("Creating user...");
        User user = User.createBy(command);
        return userRepository.save(user);
    }
}
