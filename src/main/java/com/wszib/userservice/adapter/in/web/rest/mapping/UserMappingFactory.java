package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.AddressDTO;
import com.wszib.userservice.adapter.in.web.rest.model.ChangeUserDetailsDTO;
import com.wszib.userservice.adapter.in.web.rest.model.RegisterUserDTO;
import com.wszib.userservice.adapter.in.web.rest.model.UserDTO;
import com.wszib.userservice.domain.Address;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;
import com.wszib.userservice.domain.command.RegisterUserCommand;

import java.util.function.Function;

public interface UserMappingFactory {

    static Function<Address, AddressDTO> createAddressDomainToDTOMapper() {
        return new AddressDomainToDTOMapper();
    }

    static Function<AddressDTO, Address> createAddressDTOToDomainMapper() {
        return new AddressDTOToDomainMapper();
    }

    static Function<User, UserDTO> createUserDomainToDTOMapper() {
        return new UserDomainToDTOMapper();
    }

    static Function<RegisterUserDTO, RegisterUserCommand> createUserDTOToCommandMapper() {
        return new CreateUserDTOToCommandMapper();
    }

    static Function<ChangeUserDetailsDTO, ChangeUserDetailsCommand> createChangeUserDetailsDTOToCommandMapper(String userId) {
        return new ChangeUserDetailsDTOToCommandMapper(userId);
    }
}
