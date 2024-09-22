package com.wszib.userservice.domain;

import com.wszib.userservice.domain.querry.FilterCriteria;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(String id);
    List<User> findAllBy(FilterCriteria criteria);
    User save(User user);
    void delete(String id);
    User changeUserDetails(User user);
}
