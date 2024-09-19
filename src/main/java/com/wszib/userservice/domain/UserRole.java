package com.wszib.userservice.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRole {

    private UserRoleId id;
    private UserId userId;
    private RoleName roleName;



}
