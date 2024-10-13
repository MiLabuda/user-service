package com.wszib.userservice.infrastructure.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContext {
    public static final String CORRELATION_ID = "org-correlation-id";
    public static final String AUTH_TOKEN = "org-auth-token";
    public static final String USER_ID = "org-user-id";
    public static final String ORGANIZATION_ID = "org-organization-id";

    private String correlationId = "";
    private String authToken = "";
    private String userId = "";
    private String organizationId = "";
}