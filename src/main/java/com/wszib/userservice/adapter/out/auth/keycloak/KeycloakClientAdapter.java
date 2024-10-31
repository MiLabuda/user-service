package com.wszib.userservice.adapter.out.auth.keycloak;

import com.wszib.userservice.adapter.out.auth.keycloak.config.KeycloakConfig;
import com.wszib.userservice.adapter.out.auth.keycloak.mapping.UserRepresentationMappingFactory;
import com.wszib.userservice.adapter.out.auth.keycloak.model.KeycloakSynchronizationException;
import com.wszib.userservice.application.ports.out.KeycloakUserPort;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.infrastructure.adapter.DrivenAdapter;
import jakarta.transaction.Transactional;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.function.Function;


@Repository
@Transactional
@DrivenAdapter
public class KeycloakClientAdapter implements KeycloakUserPort {

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
    public void registerUser(User user) {
        Keycloak keycloak = keycloakConfig.keycloak();
        try {
            UserRepresentation userRepresentation = userToUserRepresentationMapper.apply(user);
            keycloak
                    .realm(keycloakConfig.realm(keycloak).getRealm())
                    .users()
                    .create(userRepresentation);
            LOGGER.info("User synced with Keycloak successfully");
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
}
