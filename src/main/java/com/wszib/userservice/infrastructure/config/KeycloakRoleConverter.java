package com.wszib.userservice.infrastructure.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public static final String RESOURCE_ACCESS = "resource_access";
    // TODO export microservices as keycloak.client-id
    public static final String KEYCLOAK_CLIENT_ID = "microservices";
    public static final String ROLES = "roles";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        final var resourceAccess = (Map<String, Object>) jwt.getClaims().getOrDefault(RESOURCE_ACCESS, Map.of());
        final var clientAccess = (Map<String, Object>) resourceAccess.getOrDefault(KEYCLOAK_CLIENT_ID, Map.of());
        final var clientRoles = (List<String>) clientAccess.getOrDefault(ROLES, List.of());

        return clientRoles.stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
