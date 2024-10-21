package com.wszib.userservice.adapter.out.auth.keycloak.mapping;

import com.wszib.userservice.domain.Password;
import org.keycloak.representations.idm.CredentialRepresentation;

import java.util.function.Function;

class PasswordToCredentialsRepresentationMapper implements Function<Password, CredentialRepresentation> {

    @Override
    public CredentialRepresentation apply(Password password) {
        if(password == null) return null;

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password.getValue());
        credentialRepresentation.setTemporary(false);
        return credentialRepresentation;
    }
}
