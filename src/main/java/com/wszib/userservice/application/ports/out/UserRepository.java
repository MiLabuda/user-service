package com.wszib.userservice.application.ports.out;

import com.wszib.userservice.domain.User;
import com.wszib.userservice.domain.querry.FilterCriteria;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(String id);
    List<User> findAllBy(FilterCriteria criteria);
    void save(User user);
    void delete(String id);
    void changeUserDetails(User user);
    void changeUserStatus(String userId, boolean enabled);

    Boolean existsById(String id);
    Boolean existsByIdAndStatus(String id, boolean enabled);
}
