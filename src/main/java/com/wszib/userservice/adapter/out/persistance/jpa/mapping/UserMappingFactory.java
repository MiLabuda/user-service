package com.wszib.userservice.adapter.out.persistance.jpa.mapping;

import com.wszib.userservice.adapter.out.persistance.jpa.model.AddressData;
import com.wszib.userservice.adapter.out.persistance.jpa.model.UserData;
import com.wszib.userservice.adapter.out.persistance.jpa.model.UserRoleData;
import com.wszib.userservice.domain.Address;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.domain.UserRole;

import java.util.function.Function;

public interface UserMappingFactory {

    static Function<UserData, User> createUserDataToDomainMapper() {
        return new UserDataToDomainMapper();
    }

    static Function<User, UserData> createUserDomainToDataMapper() {
        return new UserDomainToDataMapper();
    }

    static Function<AddressData, Address> createAddressDataToDomainMapper() {
        return new AddressDataToDomainMapper();
    }

    static Function<UserRoleData, UserRole> createUserRoleDataToDomainMapper() {
        return new UserRoleDataToDomainMapper();
    }
}
