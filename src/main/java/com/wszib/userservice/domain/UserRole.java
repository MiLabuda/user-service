package com.wszib.userservice.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRole {

    private UserRoleId id;
    private UserId userId;
    private RoleName roleName;

    public static UserRole create(UserId userId, RoleName roleName){
        return UserRole.builder()
                .id(UserRoleId.generate())
                .userId(userId)
                .roleName(roleName)
                .build();
    }

}
