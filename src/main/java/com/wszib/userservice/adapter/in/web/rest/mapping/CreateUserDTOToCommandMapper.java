package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.CreateUserDTO;
import com.wszib.userservice.domain.command.CreateUserCommand;

import java.util.function.Function;

class CreateUserDTOToCommandMapper implements Function<CreateUserDTO, CreateUserCommand> {

    @Override
    public CreateUserCommand apply(CreateUserDTO createUserDTO) {
        if(createUserDTO == null) return null;

        return new CreateUserCommand(
                createUserDTO.firstName(),
                createUserDTO.lastName(),
                createUserDTO.email(),
                createUserDTO.password(),
                createUserDTO.roleName(),
                createUserDTO.addressName(),
                createUserDTO.street(),
                createUserDTO.city(),
                createUserDTO.zipCode(),
                createUserDTO.country()
        );
    }
}
