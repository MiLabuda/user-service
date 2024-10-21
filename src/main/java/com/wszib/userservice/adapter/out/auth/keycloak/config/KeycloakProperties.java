package com.wszib.userservice.adapter.out.auth.keycloak.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {

    private String serviceUrl;
    private String realm;
    private String username;
    private String password;
    private String clientId;
    private String clientSecret;

}
