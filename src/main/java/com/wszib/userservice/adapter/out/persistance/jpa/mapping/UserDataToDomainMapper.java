package com.wszib.userservice.adapter.out.persistance.jpa.mapping;

import com.wszib.userservice.adapter.out.persistance.jpa.model.AddressData;
import com.wszib.userservice.adapter.out.persistance.jpa.model.UserData;
import com.wszib.userservice.adapter.out.persistance.jpa.model.UserRoleData;
import com.wszib.userservice.domain.*;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class UserDataToDomainMapper implements Function<UserData, User> {

    Function<AddressData, Address> addressModelToDomainMapper;
    Function<UserRoleData, UserRole> userRoleModelToDomainMapper;

    UserDataToDomainMapper(){
        this(
                UserMappingFactory.createAddressDataToDomainMapper(),
                UserMappingFactory.createUserRoleDataToDomainMapper());
    }

    UserDataToDomainMapper(
            Function<AddressData, Address> addressModelToDomainMapper,
            Function<UserRoleData, UserRole> userRoleModelToDomainMapper
    ){
        this.addressModelToDomainMapper = addressModelToDomainMapper;
        this.userRoleModelToDomainMapper = userRoleModelToDomainMapper;
    }

    @Override
    public User apply(UserData userData) {
        if(userData == null) return null;

        return User.builder()
                .id(UserId.of(userData.getId()))
                .firstName(FirstName.of(userData.getFirstName()))
                .lastName(LastName.of(userData.getLastName()))
                .email(Email.of(userData.getEmail()))
                .password(Password.of(userData.getPassword()))
                .roles(Optional.ofNullable(userData.getRoles()).orElseGet(Collections::emptySet).stream().filter(Objects::nonNull).map(userRoleModelToDomainMapper).collect(Collectors.toSet()))
                .deliveryAddress(addressModelToDomainMapper.apply(userData.getDeliveryAddress()))
                .invoiceAddress(addressModelToDomainMapper.apply(userData.getInvoiceAddress()))
                .active(userData.getActive())
                .build();
    }
}
