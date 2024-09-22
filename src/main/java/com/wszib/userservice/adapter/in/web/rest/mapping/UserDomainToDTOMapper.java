package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.UserDTO;
import com.wszib.userservice.domain.User;

import java.util.function.Function;
import java.util.stream.Collectors;

class UserDomainToDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        if(user == null) return null;

        return new UserDTO(
                user.getId().value(),
                user.getFirstName().value(),
                user.getLastName().value(),
                user.getEmail().value(),
                user.getRoles().stream().map(role -> role.getRoleName().value()).collect(Collectors.toSet()),
                user.getActive()
        );
    }
}
