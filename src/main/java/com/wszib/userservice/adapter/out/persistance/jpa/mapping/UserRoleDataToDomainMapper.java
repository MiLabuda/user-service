package com.wszib.userservice.adapter.out.persistance.jpa.mapping;

import com.wszib.userservice.adapter.out.persistance.jpa.model.UserRoleData;
import com.wszib.userservice.domain.RoleName;
import com.wszib.userservice.domain.UserId;
import com.wszib.userservice.domain.UserRole;
import com.wszib.userservice.domain.UserRoleId;

import java.util.function.Function;

class UserRoleDataToDomainMapper implements Function<UserRoleData, UserRole> {

    @Override
    public UserRole apply(UserRoleData userRoleData) {
        if(userRoleData == null) return null;

        return UserRole.builder()
                .id(UserRoleId.of(userRoleData.getId()))
                .userId(UserId.of(userRoleData.getUserId()))
                .roleName(RoleName.of(userRoleData.getRoleName()))
                .build();
    }
}
