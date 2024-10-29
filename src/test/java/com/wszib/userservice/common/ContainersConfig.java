package com.wszib.userservice.common;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfig {

    static String POSTGRES_IMAGE = "postgres:14-alpine";
    static String KEYCLOAK_IMAGE = "keycloak/keycloak:25.0";
    static String realmImportFile = "/wszib-realm.json";
    static String realmName = "wszib-realm";

    @Bean
    @ServiceConnection
    @RestartScope
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>(POSTGRES_IMAGE);
    }

    @Bean
    @RestartScope
    KeycloakContainer keycloakContainer(DynamicPropertyRegistry registry) {
        var keycloak = new KeycloakContainer(KEYCLOAK_IMAGE)
                .withRealmImportFile(realmImportFile);
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "/realms/" + realmName
        );
        return keycloak;
    }

}
