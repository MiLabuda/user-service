package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.AddressDTO;
import com.wszib.userservice.adapter.in.web.rest.model.UserDTO;
import com.wszib.userservice.domain.Address;
import com.wszib.userservice.domain.User;

import java.util.function.Function;
import java.util.stream.Collectors;

class UserDomainToDTOMapper implements Function<User, UserDTO> {

    Function<Address, AddressDTO> addressAddressDTOMapper;

    UserDomainToDTOMapper() {
        this(UserMappingFactory.createAddressDomainToDTOMapper());
    }

    UserDomainToDTOMapper(Function<Address, AddressDTO> addressAddressDTOMapper) {
        this.addressAddressDTOMapper = addressAddressDTOMapper;
    }

    @Override
    public UserDTO apply(User user) {
        if(user == null) return null;

        return new UserDTO(
                user.getId().value(),
                user.getFirstName().value(),
                user.getLastName().value(),
                user.getEmail().value(),
                //TODO Think if adding UserRoleDTO would be better
                user.getRoles().stream().map(role -> role.getRoleName().value()).collect(Collectors.toSet()),
                user.getActive(),
                addressAddressDTOMapper.apply(user.getInvoiceAddress()),
                addressAddressDTOMapper.apply(user.getDeliveryAddress())
        );
    }
}
