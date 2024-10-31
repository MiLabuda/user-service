package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.AddressDTO;
import com.wszib.userservice.adapter.in.web.rest.model.RegisterUserDTO;
import com.wszib.userservice.domain.*;
import com.wszib.userservice.domain.command.RegisterUserCommand;

import java.util.function.Function;

class CreateUserDTOToCommandMapper implements Function<RegisterUserDTO, RegisterUserCommand> {

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
    public RegisterUserCommand apply(RegisterUserDTO registerUserDTO) {
        if(registerUserDTO == null) return null;

        return new RegisterUserCommand.Builder()
                .userId(UserId.generate())
                .firstName(FirstName.of(registerUserDTO.firstName()))
                .lastName(LastName.of(registerUserDTO.lastName()))
                .email(Email.of(registerUserDTO.email()))
                .password(Password.of(registerUserDTO.password()))
                .roleName(RoleName.of(registerUserDTO.roleName()))
                .invoiceAddress(addressDTOToDomainMapper.apply(registerUserDTO.invoiceAddress()))
                .deliveryAddress(addressDTOToDomainMapper.apply(registerUserDTO.deliveryAddress()))
                .build();
    }
}
