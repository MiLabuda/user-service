package com.wszib.userservice.adapter.out.persistance.jpa;

import com.wszib.userservice.adapter.out.persistance.jpa.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataRepository extends JpaRepository<UserData, String> {

}
