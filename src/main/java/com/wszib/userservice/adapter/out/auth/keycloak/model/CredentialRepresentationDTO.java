package com.wszib.userservice.adapter.out.auth.keycloak.model;

public record CredentialRepresentationDTO(
        Long createdDate,
        String credentialData,
        String id,
        Integer priority,
        String secretData,
        Boolean temporary,
        String type,
        String userLabel,
        String value
) {}