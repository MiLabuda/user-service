package com.wszib.userservice.domain;

import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;
import com.wszib.userservice.domain.command.CreateUserCommand;
import com.wszib.userservice.infrastructure.aggregate.AggregateRoot;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class User implements AggregateRoot {

    private UserId id;
    private FirstName firstName;
    private LastName lastName;
    private Email email;
    private Password password;
    private Set<UserRole> roles;
    private Address deliveryAddress;
    private Address  invoiceAddress;
    private Boolean enabled;


    public User changeDetailsBy(ChangeUserDetailsCommand cmd){
        this.firstName = cmd.firstName();
        this.lastName = cmd.lastName();
        this.email = cmd.email();
        return this;
    }

    public static User createBy(CreateUserCommand cmd){
        User user =  User.builder()
                .id(cmd.userId())
                .firstName(cmd.firstName())
                .lastName(cmd.lastName())
                .email(cmd.email())
                .password(cmd.password())
                .roles(new HashSet<>())
                .invoiceAddress(cmd.invoiceAddress())
                .deliveryAddress(cmd.deliveryAddress())
                .enabled(true)
                .build();
        user.getRoles().add(UserRole.create(user.getId(), cmd.roleName()));
        return user;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }
}
