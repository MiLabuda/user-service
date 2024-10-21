package com.wszib.userservice.adapter.out.auth.keycloak.mapping;

import com.wszib.userservice.adapter.out.auth.keycloak.model.CredentialRepresentationDTO;
import com.wszib.userservice.adapter.out.auth.keycloak.model.UserRepresentationDTO;
import com.wszib.userservice.infrastructure.utils.MapperUtils;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.function.Function;

class UserRepresentationToDTOMapper implements Function<UserRepresentation, UserRepresentationDTO> {

    Function<CredentialRepresentation, CredentialRepresentationDTO> credentialRepresentationToDTOMapper;

    public UserRepresentationToDTOMapper() {
        this(UserRepresentationMappingFactory.createCredentialsRepresentationsToDTOMapper());
    }

    public UserRepresentationToDTOMapper(
            Function<CredentialRepresentation, CredentialRepresentationDTO> credentialRepresentationToDTOMapper) {
        this.credentialRepresentationToDTOMapper = credentialRepresentationToDTOMapper;
    }

    @Override
    public UserRepresentationDTO apply(UserRepresentation userRepresentation) {
        if(userRepresentation == null) return null;

        return new UserRepresentationDTO(
                userRepresentation.getCreatedTimestamp(),
                MapperUtils.mapList(userRepresentation.getCredentials(), credentialRepresentationToDTOMapper),
                userRepresentation.getEmail(),
                userRepresentation.isEmailVerified(),
                userRepresentation.isEnabled(),
                userRepresentation.getFirstName(),
                userRepresentation.getId(),
                userRepresentation.getLastName(),
                userRepresentation.getRealmRoles(),
                userRepresentation.getRequiredActions(),
                userRepresentation.getSelf(),
                userRepresentation.getServiceAccountClientId(),
                userRepresentation.getUsername()
        );
    }
}
