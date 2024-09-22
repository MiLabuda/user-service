package com.wszib.userservice.adapter.out.persistance.jpa.mapping;

import com.wszib.userservice.adapter.out.persistance.jpa.model.AddressData;
import com.wszib.userservice.domain.Address;

import java.util.function.Function;

class AddressDomainToDataMapper implements Function<Address, AddressData>{

    @Override
    public AddressData apply(Address address) {
        if(address == null) return null;

        return AddressData.builder()
                .id(address.getId().value())
                .name(address.getName())
                .street(address.getStreet())
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .build();
    }
}
