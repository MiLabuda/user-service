package com.wszib.userservice.adapter.out.persistance.jpa.model;

import jakarta.persistence.Column;
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
@Table(name = "user_roles")
public class UserRoleData {

    @Id
    @Column
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "role_name")
    private String roleName;
}
