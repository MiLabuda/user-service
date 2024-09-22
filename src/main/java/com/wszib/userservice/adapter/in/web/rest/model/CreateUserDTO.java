package com.wszib.userservice.adapter.in.web.rest.model;

import lombok.Builder;

@Builder
public record CreateUserDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        String roleName,
        AddressDTO invoiceAddress,
        AddressDTO deliveryAddress
) {
}
