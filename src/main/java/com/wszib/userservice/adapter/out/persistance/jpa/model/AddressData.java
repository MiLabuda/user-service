package com.wszib.userservice.adapter.out.persistance.jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class AddressData {
    @Id
    private String id;
    private String name;
    private String street;
    private String city;
    private String zipCode;
    private String country;
}
