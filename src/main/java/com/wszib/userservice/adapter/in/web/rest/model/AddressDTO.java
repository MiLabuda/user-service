package com.wszib.userservice.adapter.in.web.rest.model;

import lombok.Builder;

@Builder
public record AddressDTO(
        String addressName,
        String street,
        String city,
        String zipCode,
        String country) {
}
