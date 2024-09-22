package com.wszib.userservice.application.service;

import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;
import com.wszib.userservice.domain.error.NullIdException;
import com.wszib.userservice.application.usecase.ChangeUserDetailsUseCase;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.domain.UserNotFoundException;
import com.wszib.userservice.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChangeUserDetailsService implements ChangeUserDetailsUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeUserDetailsService.class);

    private final UserRepository userRepository;

    @Override
    public User changeUserDetails(ChangeUserDetailsCommand command) {
        LOGGER.info("Changing user details...");
        String userId = command.userId().value();
        if(userId == null) throw new NullIdException();
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        User updatedUser = existingUser.changeDetailsBy(command);
        userRepository.changeUserDetails(updatedUser);
        LOGGER.info("User details changed successfully");
        return updatedUser;
    }
}
