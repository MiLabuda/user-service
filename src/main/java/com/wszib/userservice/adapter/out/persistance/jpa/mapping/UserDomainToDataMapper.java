package com.wszib.userservice.adapter.out.persistance.jpa.mapping;

import com.wszib.userservice.adapter.out.persistance.jpa.model.AddressData;
import com.wszib.userservice.adapter.out.persistance.jpa.model.UserData;
import com.wszib.userservice.adapter.out.persistance.jpa.model.UserRoleData;
import com.wszib.userservice.domain.Address;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.domain.UserRole;

import java.util.function.Function;
import java.util.stream.Collectors;

class UserDomainToDataMapper implements Function<User, UserData>{

    Function<UserRole, UserRoleData> userRoleDomainToDataMapper;
    Function<Address, AddressData> addressDomainToDataMapper;

    UserDomainToDataMapper(){
        this(new UserRoleDomainToDataMapper(), new AddressDomainToDataMapper());
    }

    UserDomainToDataMapper(Function<UserRole, UserRoleData> UserRoleDomainToDataMapper,
                           Function<Address, AddressData> AddressDomainToDataMapper){
        this.userRoleDomainToDataMapper = UserRoleDomainToDataMapper;
        this.addressDomainToDataMapper = AddressDomainToDataMapper;
    }

    @Override
    public UserData apply(User user) {
        return UserData.builder()
                .id(user.getId().value())
                .firstName(user.getFirstName().value())
                .lastName(user.getLastName().value())
                .email(user.getEmail().value())
                .password(user.getPassword().getValue())
                .roles(user.getRoles().stream().map(userRoleDomainToDataMapper).collect(Collectors.toSet()))
                .deliveryAddress(addressDomainToDataMapper.apply(user.getDeliveryAddress()))
                .invoiceAddress(addressDomainToDataMapper.apply(user.getInvoiceAddress()))
                .active(user.getEnabled())
                .build();
    }
}
