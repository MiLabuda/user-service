package com.wszib.userservice.domain;

import lombok.Getter;

@Getter
class Address {

    private AddressId id;
    private String name;
    private String street;
    private String city;
    private String zipCode;
    private String country;

}
