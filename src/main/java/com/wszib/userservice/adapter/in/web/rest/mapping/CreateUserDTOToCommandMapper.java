package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.AddressDTO;
import com.wszib.userservice.adapter.in.web.rest.model.CreateUserDTO;
import com.wszib.userservice.domain.*;
import com.wszib.userservice.domain.command.CreateUserCommand;

import java.util.function.Function;

class CreateUserDTOToCommandMapper implements Function<CreateUserDTO, CreateUserCommand> {

    Function<AddressDTO, Address> addressDTOToDomainMapper;

    CreateUserDTOToCommandMapper() {
        this(UserMappingFactory.createAddressDTOToDomainMapper());
    }

    CreateUserDTOToCommandMapper(
            Function<AddressDTO, Address> addressDTOToDomainMapper
    ){
        this.addressDTOToDomainMapper = addressDTOToDomainMapper;
    }

    @Override
    public CreateUserCommand apply(CreateUserDTO createUserDTO) {
        if(createUserDTO == null) return null;

        return new CreateUserCommand(
                FirstName.of(createUserDTO.firstName()),
                LastName.of(createUserDTO.lastName()),
                Email.of(createUserDTO.email()),
                Password.of(createUserDTO.password()),
                RoleName.of(createUserDTO.roleName()),
                addressDTOToDomainMapper.apply(createUserDTO.invoiceAddress()),
                addressDTOToDomainMapper.apply(createUserDTO.deliveryAddress())
        );
    }
}
