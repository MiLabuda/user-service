package com.wszib.userservice.domain;

import com.wszib.userservice.adapter.out.auth.keycloak.model.KeycloakSynchronizationException;
import com.wszib.userservice.application.ports.out.KeycloakClientPort;
import com.wszib.userservice.domain.command.RegisterUserCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFactory.class);
    private final KeycloakClientPort keycloakClientPort;

    public UserFactory(KeycloakClientPort keycloakClientPort) {
        this.keycloakClientPort = keycloakClientPort;
    }

    public User registerUser(RegisterUserCommand command) {
        User tempUser = User.registerBy(command);

        try {
            String keycloakUserId = keycloakClientPort.registerUser(tempUser);

            return User.builder()
                    .id(UserId.of(keycloakUserId))
                    .firstName(tempUser.getFirstName())
                    .lastName(tempUser.getLastName())
                    .email(tempUser.getEmail())
                    .password(tempUser.getPassword())
                    .roles(tempUser.getRoles())
                    .invoiceAddress(tempUser.getInvoiceAddress())
                    .deliveryAddress(tempUser.getDeliveryAddress())
                    .enabled(tempUser.getEnabled())
                    .keycloakStatus(User.KeycloakStatus.SYNCED)
                    .build();

        } catch (KeycloakSynchronizationException e) {
            tempUser.markAsError();
            LOGGER.error("Failed to synchronize User with Keycloak", e);
            throw e;
        }
    }
}