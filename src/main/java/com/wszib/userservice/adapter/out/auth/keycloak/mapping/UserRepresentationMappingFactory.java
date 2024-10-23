package com.wszib.userservice.adapter.out.auth.keycloak.mapping;

import com.wszib.userservice.adapter.out.auth.keycloak.model.CredentialRepresentationDTO;
import com.wszib.userservice.adapter.out.auth.keycloak.model.UserRepresentationDTO;
import com.wszib.userservice.domain.Password;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.domain.UserRole;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.function.Function;

public interface UserRepresentationMappingFactory {

    static Function<UserRepresentation, UserRepresentationDTO> createUserRepresentationToDTOMapper(){
        return new UserRepresentationToDTOMapper();
    }

    static Function<String, UserRole> createRealmRolesToDomainRolesMapper(){
        return new RealmRolesToDomainRolesMapper();
    }

    static Function<UserRepresentation, User> createUserRepresentationToDomainMapper(){
        return new UserRepresentationToDomainMapper();
    }

    static Function<CredentialRepresentation, CredentialRepresentationDTO> createCredentialsRepresentationsToDTOMapper(){
        return new CredentialsRepresentationsToDTOMapper();
    }

    static Function<User, UserRepresentation> createUserToUserRepresentationMapper(){
        return new UserDomainToUserRepresentationMapper();
    }

    static Function<Password, CredentialRepresentation> createPasswordToCredentialsRepresentationMapper(){
        return new PasswordToCredentialsRepresentationMapper();
    }
}
