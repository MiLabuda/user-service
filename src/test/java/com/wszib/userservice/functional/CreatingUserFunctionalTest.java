package com.wszib.userservice.functional;

import com.wszib.userservice.adapter.in.web.rest.UserController;
import com.wszib.userservice.common.ContainersConfig;
import com.wszib.userservice.common.UserTestUtils;
import io.restassured.RestAssured;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.stream.Stream;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@Import(ContainersConfig.class)
@ActiveProfiles("test")
@DisplayName("Creating user")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CreatingUserFunctionalTest {

    @LocalServerPort
    private int port;

    @Getter
    private final UserController api;

    @Getter
    UserTestUtils userTestHelper;

    @Autowired
    CreatingUserFunctionalTest(UserController api, UserTestUtils userTestHelper) {
        this.api = api;
        this.userTestHelper = userTestHelper;
    }

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @ParameterizedTest(name = "{index} - Token Type: {0}, Expected Status: {1}")
    @MethodSource("userRoleProvider")
    @DisplayName("Should create or deny user creation based on role authorization")
    void createUserWithAuthorization(UserTestUtils.TokenType tokenType, HttpStatus expectedStatus) {
        userTestHelper.sendPostRequestAndCompareStatus(
                tokenType,
                expectedStatus
        );
    }

    private static Stream<Object[]> userRoleProvider() {
        return Stream.of(
                new Object[]{UserTestUtils.TokenType.KEYCLOAK_CLIENT, HttpStatus.CREATED},
                new Object[]{UserTestUtils.TokenType.ADMIN, HttpStatus.CREATED},
                new Object[]{UserTestUtils.TokenType.CUSTOMER, HttpStatus.FORBIDDEN},
                new Object[]{UserTestUtils.TokenType.UNAUTHORIZED, HttpStatus.UNAUTHORIZED}
        );
    }
}
