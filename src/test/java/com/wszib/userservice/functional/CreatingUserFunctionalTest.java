package com.wszib.userservice.functional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wszib.userservice.adapter.in.web.rest.UserController;
import com.wszib.userservice.adapter.in.web.rest.model.CreateUserDTO;
import com.wszib.userservice.common.ContainersConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static java.util.Collections.singletonList;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@Import(ContainersConfig.class)
@ActiveProfiles("test")
@DisplayName("Creating user")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreatingUserFunctionalTest {

    static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";

    @Value("${keycloak.client-id}")
    private String client_id;

    @Value("${keycloak.client-secret}")
    private String client_secret;

    @LocalServerPort
    private int port;

    @Getter
    private final UserController api;

    @Autowired
    CreatingUserFunctionalTest(UserController api) {
        this.api = api;
    }

    @Autowired
    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Should create user when authorized")
    void createUserAuthorized() {
        final CreateUserDTO request = constructRequest();
        given().
                header(HttpHeaders.AUTHORIZATION, "Bearer " + getToken()).
                contentType(ContentType.JSON).
                body(request).
                when().
                post("/v1/users").
                then().
                statusCode(HttpStatus.CREATED.value());

    }

    @Test
    @DisplayName("Should not create user when unauthorized")
    void createUserUnauthorized() {
        final CreateUserDTO request = constructRequest();
        given().
                contentType(ContentType.JSON).
                body(request).
                when().
                post("/v1/users").
                then().
                statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    private CreateUserDTO constructRequest() {
        return new CreateUserDTO(
                "John",
                "Doe",
                "john.doe@gmail.com",
                "securePassword1/",
                "CUSTOMER",
                null,
                null
        );
    }

    private String getToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("grant_type", singletonList(GRANT_TYPE_CLIENT_CREDENTIALS));
        map.put("client_id", singletonList(client_id));
        map.put("client_secret", singletonList(client_secret));

        String authServerUrl = oAuth2ResourceServerProperties.getJwt().getIssuerUri() + "/protocol/openid-connect/token";

        var request = new HttpEntity<>(map, httpHeaders);
        KeyCloakToken token = restTemplate.postForObject(
                authServerUrl,
                request,
                KeyCloakToken.class
        );

        assert token != null;
        return token.accessToken();
    }

    record KeyCloakToken(@JsonProperty("access_token") String accessToken) {}
}
