package com.wszib.userservice.application.service;

import com.wszib.userservice.application.ports.in.RemoveUserUseCase;
import com.wszib.userservice.application.ports.out.KeycloakUserPort;
import com.wszib.userservice.application.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RemoveUserService implements RemoveUserUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveUserService.class);

    private final UserRepository userRepository;
    private final KeycloakUserPort keycloakUserPort;

    @Override
    public void remove(String id) {
        LOGGER.info("Removing user ...");
        userRepository.delete(id);
        keycloakUserPort.deleteUser(id);
    }
}
