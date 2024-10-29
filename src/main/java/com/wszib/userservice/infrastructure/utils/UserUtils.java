package com.wszib.userservice.infrastructure.utils;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@UtilityClass
public class UserUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserUtils.class);

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            LOGGER.warn("No user is currently authenticated.");
            return null;
        }

        LOGGER.info("Current user ID retrieved: {}", authentication.getName());
        return authentication.getName();
    }

    public Collection<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            LOGGER.warn("No user is currently authenticated.");
            return Collections.emptyList();
        }

        Collection<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        LOGGER.info("Roles for current user retrieved: {}", roles);
        return roles;
    }

    public boolean isAdmin() {
        return getCurrentUserRoles().contains("ROLE_ADMIN");
    }

    public boolean isSelf(String userId) {
        return getCurrentUserId().equals(userId);
    }

}
