package com.wszib.userservice.adapter.out.persistance.jpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserData {

    @Id
    private String id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoleData> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_address_id")
    private AddressData deliveryAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_address_id")
    private AddressData  invoiceAddress;

    private Boolean enabled;
}
