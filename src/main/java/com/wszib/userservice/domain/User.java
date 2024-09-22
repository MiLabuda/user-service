package com.wszib.userservice.domain;

import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;
import com.wszib.userservice.domain.command.CreateUserCommand;
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
    private Boolean active;


    public User changeDetailsBy(ChangeUserDetailsCommand cmd){
        this.firstName = new FirstName(cmd.firstName());
        this.lastName = new LastName(cmd.lastName());
        this.email = new Email(cmd.email());
        return this;
    }

    public static User createBy(CreateUserCommand cmd){
        User user =  User.builder()
                .id(UserId.generate())
                .firstName(cmd.firstName())
                .lastName(cmd.lastName())
                .email(cmd.email())
                //TODO Implement password hashing
                .password(cmd.password())
                .roles(new HashSet<>())
                .invoiceAddress(cmd.invoiceAddress())
                .deliveryAddress(cmd.deliveryAddress())
                .active(true)
                .build();
        user.getRoles().add(UserRole.create(user.getId(), cmd.roleName()));
        return user;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
