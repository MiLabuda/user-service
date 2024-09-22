package com.wszib.userservice.adapter.out.persistance.jpa.mapping;

import com.wszib.userservice.adapter.out.persistance.jpa.model.AddressData;
import com.wszib.userservice.domain.Address;
import com.wszib.userservice.domain.AddressId;

import java.util.function.Function;

class AddressDataToDomainMapper implements Function<AddressData, Address> {

    @Override
    public Address apply(AddressData addressData) {
        if(addressData == null) return null;

        return Address.builder()
                .id(new AddressId(addressData.getId()))
                .name(addressData.getName())
                .street(addressData.getStreet())
                .city(addressData.getCity())
                .zipCode(addressData.getZipCode())
                .country(addressData.getCountry())
                .build();
    }
}
