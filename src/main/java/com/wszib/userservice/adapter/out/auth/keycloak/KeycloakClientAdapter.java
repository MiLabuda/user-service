package com.wszib.userservice.adapter.out.auth.keycloak;

import com.wszib.userservice.adapter.out.auth.keycloak.config.KeycloakConfig;
import com.wszib.userservice.adapter.out.auth.keycloak.mapping.UserRepresentationMappingFactory;
import com.wszib.userservice.adapter.out.auth.keycloak.model.KeycloakSynchronizationException;
import com.wszib.userservice.application.ports.out.KeycloakClientPort;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.infrastructure.adapter.DrivenAdapter;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.function.Function;


@Repository
@Transactional
@DrivenAdapter
public class KeycloakClientAdapter implements KeycloakClientPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakClientAdapter.class);

    Function<UserRepresentation, User> userRepresentationToDomainMapper;
    Function<User, UserRepresentation> userToUserRepresentationMapper;

    private final KeycloakConfig keycloakConfig;

    @Autowired
    KeycloakClientAdapter(KeycloakConfig keycloakConfig){
        this(keycloakConfig,
                UserRepresentationMappingFactory.createUserRepresentationToDomainMapper(),
                UserRepresentationMappingFactory.createUserToUserRepresentationMapper()
        );
    }

    KeycloakClientAdapter(KeycloakConfig keycloakConfig,
                          Function<UserRepresentation, User> userRepresentationToDomainMapper,
                          Function<User, UserRepresentation> userToUserRepresentationMapper){
        this.keycloakConfig = keycloakConfig;
        this.userRepresentationToDomainMapper = userRepresentationToDomainMapper;
        this.userToUserRepresentationMapper = userToUserRepresentationMapper;
    }

    @Override
    public User getUser(String userId) {
        LOGGER.debug("Getting user from Keycloak with id: {}", userId);
        try (Keycloak keycloak = keycloakConfig.keycloak()) {
            UserRepresentation keycloakUser =
                    keycloak
                            .realm(keycloakConfig.realm(keycloak).getRealm())
                            .users()
                            .get(userId)
                            .toRepresentation();
            return userRepresentationToDomainMapper.apply(keycloakUser);
        } catch (Exception e) {
            LOGGER.error("Error retrieving user from Keycloak", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String registerUser(User user) {
        Keycloak keycloak = keycloakConfig.keycloak();
        try {
            UserRepresentation userRepresentation = userToUserRepresentationMapper.apply(user);

            try (Response response = keycloak
                    .realm(keycloakConfig.realm(keycloak).getRealm())
                    .users()
                    .create(userRepresentation)) {

                if (response.getStatus() == 201) {
                    String locationHeader = response.getHeaderString("Location");
                    String userId = extractUserIdFromLocationHeader(locationHeader);

                    LOGGER.info("User synced with Keycloak successfully, userId: {}", userId);
                    return userId;
                } else {
                    String errorMessage = response.readEntity(String.class);
                    LOGGER.error("Failed to create user in Keycloak, status: {}, error: {}", response.getStatus(), errorMessage);
                    throw new KeycloakSynchronizationException("Failed to sync user with Keycloak, status: " + response.getStatus());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error syncing user with Keycloak", e);
            throw new KeycloakSynchronizationException("Failed to sync user with Keycloak", e);
        }
    }

    @Override
    public void deleteUser(String userId) {
        try (Keycloak keycloak = keycloakConfig.keycloak()) {
            keycloak
                    .realm(keycloakConfig.realm(keycloak).getRealm())
                    .users()
                    .delete(userId);
            LOGGER.info("User removed from Keycloak successfully");
        } catch (Exception e) {
            LOGGER.error("Error removing user from Keycloak", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        try (Keycloak keycloak = keycloakConfig.keycloak()) {
            keycloak
                    .realm(keycloakConfig.realm(keycloak).getRealm())
                    .users()
                    .get(user.getId().value())
                    .update(userToUserRepresentationMapper.apply(user));
            LOGGER.info("User updated with Keycloak successfully");
        } catch (Exception e) {
            LOGGER.error("Error updating user with Keycloak", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUserStatus(String userId, boolean enabled) {
        Keycloak keycloak = keycloakConfig.keycloak();
        try {
            UserResource userResource = keycloak
                    .realm(keycloakConfig.realm(keycloak).getRealm())
                    .users()
                    .get(userId);

            UserRepresentation userRepresentation = userResource.toRepresentation();
            userRepresentation.setEnabled(enabled);

            userResource.update(userRepresentation);
            LOGGER.info("User status updated successfully in Keycloak");
        } catch (Exception e) {
            LOGGER.error("Error updating user status in Keycloak", e);
            throw new KeycloakSynchronizationException("Failed to sync user with Keycloak", e);
        }
    }

    private String extractUserIdFromLocationHeader(String locationHeader) {
        if (locationHeader == null || locationHeader.isEmpty()) {
            throw new KeycloakSynchronizationException("Location header is missing in the response from Keycloak.");
        }

        return locationHeader.substring(locationHeader.lastIndexOf('/') + 1);
    }

}
