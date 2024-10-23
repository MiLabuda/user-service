package com.wszib.userservice.adapter.out.auth.keycloak.mapping;

import com.wszib.userservice.domain.RoleName;
import com.wszib.userservice.domain.UserRole;

import java.util.function.Function;

class RealmRolesToDomainRolesMapper implements Function<String, UserRole> {

    @Override
    public UserRole apply(String roleName) {
        if (roleName == null) return null;
        return UserRole.builder()
                .roleName(RoleName.of(roleName))
                .build();
    }
}
