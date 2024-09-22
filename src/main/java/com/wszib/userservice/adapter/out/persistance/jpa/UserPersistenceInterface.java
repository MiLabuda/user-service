package com.wszib.userservice.adapter.out.persistance.jpa;

import com.wszib.userservice.adapter.out.persistance.jpa.model.UserData;
import com.wszib.userservice.domain.querry.FilterCriteria;

import java.util.List;
import java.util.Optional;

public interface UserPersistenceInterface {

    List<UserData> findAllBy(FilterCriteria filterCriteria);

    Optional<UserData> findById(String id);

    UserData save(UserData userData);

    void delete(String id);

}