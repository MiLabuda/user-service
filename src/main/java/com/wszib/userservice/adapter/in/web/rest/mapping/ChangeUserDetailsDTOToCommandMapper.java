package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.ChangeUserDetailsDTO;
import com.wszib.userservice.domain.Email;
import com.wszib.userservice.domain.FirstName;
import com.wszib.userservice.domain.LastName;
import com.wszib.userservice.domain.UserId;
import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;

import java.util.function.Function;

class ChangeUserDetailsDTOToCommandMapper implements Function<ChangeUserDetailsDTO, ChangeUserDetailsCommand>   {

    private final String customerId;

    public ChangeUserDetailsDTOToCommandMapper(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public ChangeUserDetailsCommand apply(ChangeUserDetailsDTO changeUserDetailsDTO) {
        if(changeUserDetailsDTO == null) return null;

        return new ChangeUserDetailsCommand(
                UserId.of(customerId),
                FirstName.of(changeUserDetailsDTO.firstName()),
                LastName.of(changeUserDetailsDTO.lastName()),
                Email.of(changeUserDetailsDTO.email())
        );
    }


}
