package com.wszib.userservice.adapter.out.persistance.jpa;

import com.wszib.userservice.adapter.out.persistance.jpa.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface SpringDataRepository extends JpaRepository<UserData, String> {

    @Modifying
    @Query("UPDATE UserData u SET u.enabled = :enabled WHERE u.id = :userId")
    void updateUserStatusById(@Param("userId") String userId, @Param("enabled") boolean enabled);

    boolean existsByIdAndEnabled(String id, boolean enabled);
}
