package com.wszib.userservice.adapter.out.auth.keycloak.mapping;

import com.wszib.userservice.domain.*;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class UserRepresentationToDomainMapper implements Function<UserRepresentation, User> {

    Function<String, UserRole> userRoleModelToDomainMapper;

    public UserRepresentationToDomainMapper() {
        this(UserRepresentationMappingFactory.createRealmRolesToDomainRolesMapper());
    }

    public UserRepresentationToDomainMapper(Function<String, UserRole> userRoleModelToDomainMapper) {
        this.userRoleModelToDomainMapper = userRoleModelToDomainMapper;
    }

    @Override
    public User apply(UserRepresentation userRepresentation) {
        if (userRepresentation == null) return null;

       return User.builder()
                .id(UserId.of(userRepresentation.getId()))
                .firstName(FirstName.of(userRepresentation.getFirstName()))
                .lastName(LastName.of(userRepresentation.getLastName()))
                .email(Email.of(userRepresentation.getEmail()))
                .password(Password.of(userRepresentation.getCredentials().getFirst().getValue()))
                .roles(Optional.ofNullable(userRepresentation.getRealmRoles()).orElseGet(Collections::emptyList).stream().filter(Objects::nonNull).map(userRoleModelToDomainMapper).collect(Collectors.toSet()))
                .deliveryAddress(Address.builder().build())
                .invoiceAddress(Address.builder().build())
                .enabled(userRepresentation.isEnabled())
                .build();
    }
}
