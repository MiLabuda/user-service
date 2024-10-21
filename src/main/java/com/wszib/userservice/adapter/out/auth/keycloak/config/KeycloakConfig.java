package com.wszib.userservice.adapter.out.auth.keycloak.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.RealmRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    KeycloakProperties keycloakProperties;

    public KeycloakConfig(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.getServiceUrl())
                .realm(keycloakProperties.getRealm())
                .username(keycloakProperties.getUsername())
                .password(keycloakProperties.getPassword())
                .clientId(keycloakProperties.getClientId())
                .clientSecret(keycloakProperties.getClientSecret())
                .build();
    }

    @Bean
    public RealmRepresentation realm(Keycloak keycloak) {
        return keycloak.realm(keycloakProperties.getRealm()).toRepresentation();
    }

}
