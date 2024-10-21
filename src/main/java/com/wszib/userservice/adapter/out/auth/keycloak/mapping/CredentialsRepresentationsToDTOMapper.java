package com.wszib.userservice.adapter.out.auth.keycloak.mapping;

import com.wszib.userservice.adapter.out.auth.keycloak.model.CredentialRepresentationDTO;
import org.keycloak.representations.idm.CredentialRepresentation;
import java.util.function.Function;

class CredentialsRepresentationsToDTOMapper implements Function<CredentialRepresentation, CredentialRepresentationDTO> {

    @Override
    public CredentialRepresentationDTO apply(CredentialRepresentation credentialRepresentation) {
        if(credentialRepresentation == null) return null;

        return new CredentialRepresentationDTO(
                credentialRepresentation.getCreatedDate(),
                credentialRepresentation.getCredentialData(),
                credentialRepresentation.getId(),
                credentialRepresentation.getPriority(),
                credentialRepresentation.getSecretData(),
                credentialRepresentation.isTemporary(),
                credentialRepresentation.getType(),
                credentialRepresentation.getUserLabel(),
                credentialRepresentation.getValue()
        );
    }
}
