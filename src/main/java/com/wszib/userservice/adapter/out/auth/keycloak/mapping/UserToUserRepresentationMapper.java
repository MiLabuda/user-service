package com.wszib.userservice.adapter.out.auth.keycloak.mapping;

import com.wszib.userservice.domain.Password;
import com.wszib.userservice.domain.User;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Collections;
import java.util.function.Function;

class UserToUserRepresentationMapper implements Function<User, UserRepresentation> {

    Function<Password, CredentialRepresentation> passwordToCredentialsRepresentationMapper;

    public UserToUserRepresentationMapper() {
        this(UserRepresentationMappingFactory.createPasswordToCredentialsRepresentationMapper());
    }

    public UserToUserRepresentationMapper(Function<Password, CredentialRepresentation> passwordToCredentialsRepresentationMapper) {
        this.passwordToCredentialsRepresentationMapper = passwordToCredentialsRepresentationMapper;
    }

    @Override
    public UserRepresentation apply(User user) {
        if(user == null) return null;

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(user.getId().value());
        userRepresentation.setUsername(user.getEmail().value());
        userRepresentation.setEmail(user.getEmail().value());
        userRepresentation.setFirstName(user.getFirstName().value());
        userRepresentation.setLastName(user.getLastName().value());
        userRepresentation.setEnabled(user.getEnabled());
        //TODO Maybe to implement something about email verification
        userRepresentation.setEmailVerified(true);
        userRepresentation.setCredentials(
                Collections.singletonList(passwordToCredentialsRepresentationMapper.apply(user.getPassword()))
        );
        return userRepresentation;
    }
}
