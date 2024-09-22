package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.AddressDTO;
import com.wszib.userservice.domain.Address;

import java.util.function.Function;

class AddressDomainToDTOMapper implements Function<Address, AddressDTO> {
    @Override
    public AddressDTO apply(Address address) {
        if(address == null) return null;

        return new AddressDTO(
                address.getName(),
                address.getStreet(),
                address.getCity(),
                address.getZipCode(),
                address.getCountry()
        );
    }
}
