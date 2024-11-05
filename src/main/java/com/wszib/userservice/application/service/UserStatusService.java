package com.wszib.userservice.application.service;

import com.wszib.userservice.application.ports.in.ChangeUserStatusUseCase;
import com.wszib.userservice.application.ports.out.KeycloakClientPort;
import com.wszib.userservice.application.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserStatusService implements ChangeUserStatusUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStatusService.class);

    private final UserRepository userRepository;
    private final KeycloakClientPort keycloakClientPort;

    @Override
    public void enableUser(String userId) {
    LOGGER.info("Enabling user: {}", userId);
    keycloakClientPort.updateUserStatus(userId, true);
    userRepository.changeUserStatus(userId, true);
    LOGGER.info("User enabled successfully");
    }

    @Override
    public void disableUser(String userId) {
    LOGGER.info("Disabling user: {}", userId);
    keycloakClientPort.updateUserStatus(userId, false);
    userRepository.changeUserStatus(userId, false);
    LOGGER.info("User disabled successfully");
    }
}
