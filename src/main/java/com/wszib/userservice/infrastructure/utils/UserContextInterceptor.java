package com.wszib.userservice.infrastructure.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserContextInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        headers.add(UserContext.USER_ID, UserContextHolder.getContext().getUserId());
        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());
        headers.add(UserContext.ORGANIZATION_ID, UserContextHolder.getContext().getOrganizationId());

        return execution.execute(request, body);
    }
}