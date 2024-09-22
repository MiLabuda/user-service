package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.ChangeUserDetailsDTO;
import com.wszib.userservice.adapter.in.web.rest.model.CreateUserDTO;
import com.wszib.userservice.adapter.in.web.rest.model.UserDTO;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;
import com.wszib.userservice.domain.command.CreateUserCommand;

import java.util.function.Function;

public interface UserMappingFactory {

    static Function<User, UserDTO> createUserDomainToDTOMapper() {
        return new UserDomainToDTOMapper();
    }

    static Function<CreateUserDTO, CreateUserCommand> createUserDTOToCommandMapper() {
        return new CreateUserDTOToCommandMapper();
    }

    static Function<ChangeUserDetailsDTO, ChangeUserDetailsCommand> createChangeUserDetailsDTOToCommandMapper(String userId) {
        return new ChangeUserDetailsDTOToCommandMapper(userId);
    }
}
