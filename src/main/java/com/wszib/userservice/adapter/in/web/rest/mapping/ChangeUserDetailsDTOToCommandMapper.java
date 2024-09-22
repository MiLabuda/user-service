package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.ChangeUserDetailsDTO;
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
                customerId,
                changeUserDetailsDTO.firstName(),
                changeUserDetailsDTO.lastName(),
                changeUserDetailsDTO.email()
        );
    }


}
