package com.wszib.userservice.application.ports.out;

import com.wszib.userservice.adapter.out.auth.keycloak.model.UserRepresentationDTO;
import com.wszib.userservice.domain.User;

public interface KeycloakUserPort {
    UserRepresentationDTO getUser(String userId);
    void createUser(User user);
    void deleteUser(String userId);
    void updateUser(User user);
}
