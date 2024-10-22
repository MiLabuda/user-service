package com.wszib.userservice.adapter.out.persistance.jpa;

import com.wszib.userservice.adapter.out.persistance.jpa.model.UserData;
import com.wszib.userservice.domain.querry.FilterCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserPersistenceInterface {

    private final SpringDataRepository springDataRepository;

    @Autowired
    public UserPersistenceAdapter(SpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public List<UserData> findAllBy(FilterCriteria filterCriteria) {
        //TODO: implement filtering
        return springDataRepository.findAll();
    }

    @Override
    public Optional<UserData> findById(String id) {
        return springDataRepository.findById(id);
    }

    @Override
    public void save(UserData userData) {
        springDataRepository.save(userData);
    }

    @Override
    public void delete(String id) {
        springDataRepository.deleteById(id);
    }
}
