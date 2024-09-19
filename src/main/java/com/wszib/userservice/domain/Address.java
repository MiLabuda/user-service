package com.wszib.userservice.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Address {

    private AddressId id;
    private String name;
    private String street;
    private String city;
    private String zipCode;
    private String country;

}
