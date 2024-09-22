package com.wszib.userservice.adapter.out.persistance.jpa;

import com.wszib.userservice.adapter.out.persistance.jpa.model.UserData;
import com.wszib.userservice.domain.querry.FilterCriteria;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
class InMemoryUserRepository implements UserPersistenceInterface{
    private final Map<String, UserData> storage = new HashMap<>();

    @Override
    public List<UserData> findAllBy(FilterCriteria filterCriteria) {
        return storage.values().stream()
                .filter(user -> filterCriteria.email().map(user.getEmail()::equals).orElse(true))
                .filter(user -> filterCriteria.firstName().map(user.getFirstName()::equals).orElse(true))
                .filter(user -> filterCriteria.lastName().map(user.getLastName()::equals).orElse(true))
                .filter(user -> filterCriteria.active().map(user.getActive()::equals).orElse(true))
                .collect(Collectors.toList());
    }

    public Optional<UserData> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public UserData save(UserData user) {
        storage.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(String id) {
        storage.remove(id);
    }
}
