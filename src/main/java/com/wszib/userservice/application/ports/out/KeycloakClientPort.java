package com.wszib.userservice.application.ports.out;

import com.wszib.userservice.domain.User;

public interface KeycloakClientPort {
    User getUser(String userId);
    String registerUser(User user);
    void deleteUser(String userId);
    void updateUser(User user);
    void updateUserStatus(String userId, boolean enabled);
}
