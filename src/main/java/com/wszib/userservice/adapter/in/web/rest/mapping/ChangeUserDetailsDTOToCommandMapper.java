package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.ChangeUserDetailsDTO;
import com.wszib.userservice.domain.Email;
import com.wszib.userservice.domain.FirstName;
import com.wszib.userservice.domain.LastName;
import com.wszib.userservice.domain.UserId;
import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;

import java.util.function.Function;

class ChangeUserDetailsDTOToCommandMapper implements Function<ChangeUserDetailsDTO, ChangeUserDetailsCommand>   {

    private final String userId;

    public ChangeUserDetailsDTOToCommandMapper(String userId) {
        this.userId = userId;
    }

    @Override
    public ChangeUserDetailsCommand apply(ChangeUserDetailsDTO changeUserDetailsDTO) {
        if(changeUserDetailsDTO == null) return null;

        return new ChangeUserDetailsCommand.Builder()
                .userId(UserId.of(userId))
                .firstName(FirstName.of(changeUserDetailsDTO.firstName()))
                .lastName(LastName.of(changeUserDetailsDTO.lastName()))
                .email(Email.of(changeUserDetailsDTO.email()))
                .build();
    }


}
