package com.wszib.userservice.application.ports.out;

import com.wszib.userservice.domain.User;

public interface KeycloakUserPort {
    User getUser(String userId);
    void registerUser(User user);
    void deleteUser(String userId);
    void updateUser(User user);
}
