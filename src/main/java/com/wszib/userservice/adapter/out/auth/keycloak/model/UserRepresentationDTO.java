package com.wszib.userservice.adapter.out.auth.keycloak.model;


import java.util.List;

public record UserRepresentationDTO(
        Long createdTimestamp,
        List<CredentialRepresentationDTO> credentials,
        String email,
        Boolean emailVerified,
        Boolean enabled,
        String firstName,
        String id,
        String lastName,
        List<String> realmRoles,
        List<String> requiredActions,
        String self,
        String serviceAccountClientId,
        String username
) {}
