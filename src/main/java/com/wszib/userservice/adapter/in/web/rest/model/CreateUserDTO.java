package com.wszib.userservice.adapter.in.web.rest.model;

public record CreateUserDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        String roleName,
        String addressName,
        String street,
        String city,
        String zipCode,
        String country
        //TODO Implement sameInvoiceAddress logic
) {
}
