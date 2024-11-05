package com.wszib.userservice.application.ports.in;

public interface ChangeUserStatusUseCase {
        void disableUser(String userId);
        void enableUser(String userId);
}
