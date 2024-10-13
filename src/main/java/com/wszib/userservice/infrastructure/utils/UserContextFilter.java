package com.wszib.userservice.infrastructure.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserContextFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserContextFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        UserContextHolder.getContext()
                .setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));
        UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        UserContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        UserContextHolder.getContext().setOrganizationId(httpServletRequest.getHeader(UserContext.ORGANIZATION_ID));

        if (LOGGER.isDebugEnabled()) {
            UserContext context = UserContextHolder.getContext();
            LOGGER.debug("UserContextFilter Correlation id: {}", context.getCorrelationId());
            LOGGER.debug("UserContextFilter User id: {}", context.getUserId());
            LOGGER.debug("UserContextFilter Auth token: {}", context.getAuthToken());
            LOGGER.debug("UserContextFilter Organization id: {}", context.getOrganizationId());
        }

        filterChain.doFilter(httpServletRequest, servletResponse);
    }
}