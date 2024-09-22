package com.wszib.userservice.adapter.out.persistance.jpa.mapping;

import com.wszib.userservice.adapter.out.persistance.jpa.model.UserRoleData;
import com.wszib.userservice.domain.UserRole;

import java.util.function.Function;

class UserRoleDomainToDataMapper implements Function<UserRole, UserRoleData>{

    @Override
    public UserRoleData apply(UserRole userRole) {
        return UserRoleData.builder()
                .id(userRole.getId().value())
                .userId(userRole.getUserId().value())
                .roleName(userRole.getRoleName().value())
                .build();
    }
}
