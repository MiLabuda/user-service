package com.wszib.userservice.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wszib.userservice.adapter.in.web.rest.model.CreateUserDTO;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static io.restassured.RestAssured.given;
import static java.util.Collections.singletonList;

@Component
public class UserTestUtils {

    public enum TokenType {
        KEYCLOAK_CLIENT, ADMIN, CUSTOMER, UNAUTHORIZED
    }

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.username}")
    private String adminUsername;

    @Value("${keycloak.password}")
    private String adminPassword;

    @Autowired
    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendPostRequestAndCompareStatus(TokenType tokenType, HttpStatus expectedStatus) {
        CreateUserDTO request = constructRequest();
        String token = getToken(tokenType);
        given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/v1/users")
                .then()
                .statusCode(expectedStatus.value());
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

    private String getToken(TokenType tokenType) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        switch (tokenType) {
            case KEYCLOAK_CLIENT -> {
                map.put("grant_type", singletonList("client_credentials"));
                map.put("client_id", singletonList(clientId));
                map.put("client_secret", singletonList(clientSecret));
            }
            case ADMIN -> {
                map.put("grant_type", singletonList("password"));
                map.put("username", singletonList(adminUsername));
                map.put("password", singletonList(adminPassword));
                map.put("client_id", singletonList(clientId));
                map.put("client_secret", singletonList(clientSecret));
            }
            case CUSTOMER -> {
                map.put("grant_type", singletonList("password"));
                map.put("username", singletonList("bilbo.baggins@shire.com"));
                map.put("password", singletonList("securePassword1/"));
                map.put("client_id", singletonList(clientId));
                map.put("client_secret", singletonList(clientSecret));
            }
            case UNAUTHORIZED -> { return null;}

        }
        return getToken(map);
    }



    private String getToken(MultiValueMap<String, String> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String authServerUrl = oAuth2ResourceServerProperties.getJwt().getIssuerUri() + "/protocol/openid-connect/token";

        var request = new HttpEntity<>(map, headers);
        KeyCloakToken token = restTemplate.postForObject(authServerUrl, request, KeyCloakToken.class);

        assert token != null;
        return token.accessToken();
    }

    record KeyCloakToken(@JsonProperty("access_token") String accessToken) {}
}
