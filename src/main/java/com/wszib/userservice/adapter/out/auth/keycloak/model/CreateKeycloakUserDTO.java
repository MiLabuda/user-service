package com.wszib.userservice.adapter.out.auth.keycloak.model;

import java.util.List;

public record CreateKeycloakUserDTO(
        String username,
        String password,
        String email,
        String firstName,
        String lastName,
        Boolean enabled,
        Boolean emailVerified,
        List<String> clientRoles
) {
}
