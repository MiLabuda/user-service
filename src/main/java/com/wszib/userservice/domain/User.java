package com.wszib.userservice.domain;

import com.wszib.userservice.application.command.ChangeUserDetailsCommand;
import com.wszib.userservice.application.command.CreateUserCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
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

    public User changeDetailsBy(ChangeUserDetailsCommand cmd){
        this.firstName = new FirstName(cmd.firstName());
        this.lastName = new LastName(cmd.lastName());
        this.email = new Email(cmd.email());
        return this;
    }

    public static User createBy(CreateUserCommand cmd){
        User user =  User.builder()
                .id(UserId.generate())
                .firstName(new FirstName(cmd.firstName()))
                .lastName(new LastName(cmd.lastName()))
                .email(new Email(cmd.email()))
                .password(new Password(cmd.password()))
                .roles(new HashSet<>())
                .build();
        user.getRoles().add(UserRole.create(user.getId(), RoleName.valueOf(cmd.roleName())));
        return user;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
