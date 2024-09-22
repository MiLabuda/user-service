package com.wszib.userservice.adapter.in.web.rest.model;

import lombok.Builder;

import java.util.Set;

@Builder
public record UserDTO(
        String id,
        String firstName,
        String lastName,
        String email,
        Set<String> roles,
        Boolean active,
        AddressDTO invoiceAddress,
        AddressDTO deliveryAddress
) {}
