package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.AddressDTO;
import com.wszib.userservice.domain.Address;

import java.util.function.Function;

class AddressDTOToDomainMapper implements Function<AddressDTO, Address> {

    @Override
    public Address apply(AddressDTO addressDTO) {
        if(addressDTO == null) return null;

        return Address.builder()
                .name(addressDTO.addressName())
                .street(addressDTO.street())
                .city(addressDTO.city())
                .zipCode(addressDTO.zipCode())
                .country(addressDTO.country())
                .build();
    }
}
