package com.wszib.userservice.adapter.out.persistance.jpa.mapping;

import com.wszib.userservice.adapter.out.persistance.jpa.model.UserData;
import com.wszib.userservice.domain.User;

import java.util.function.Function;

public interface UserMappingFactory {

    static Function<UserData, User> createUserDataToDomainMapper() {
        return new UserDataToDomainMapper();
    }

    static Function<User, UserData> createUserDomainToDataMapper() {
        return new UserDomainToDataMapper();
    }
}
