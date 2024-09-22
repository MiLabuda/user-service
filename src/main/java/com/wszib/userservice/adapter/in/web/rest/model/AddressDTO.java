package com.wszib.userservice.adapter.in.web.rest.model;

public record AddressDTO(
        String addressName,
        String street,
        String city,
        String zipCode,
        String country) {
}
