package com.wszib.userservice.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class User {

    private UserId id;
    private FirstName firstName;
    private LastName lastName;
    private Email email;
    private Password password;
    private Set<UserRole> roles;
    private Address deliveryAddress;
    private Address  invoiceAddress;
    private Boolean active = false;
}
