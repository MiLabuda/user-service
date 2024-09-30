package com.wszib.userservice.adapter.out.persistance.jpa;

import com.wszib.userservice.adapter.out.persistance.jpa.model.UserData;
import com.wszib.userservice.domain.querry.FilterCriteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class SpringDataRepository implements UserPersistenceInterface {
    @Override
    public List<UserData> findAllBy(FilterCriteria filterCriteria) {
        return null;
    }

    @Override
    public Optional<UserData> findById(String id) {
        return Optional.empty();
    }

    @Override
    public UserData save(UserData userData) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
