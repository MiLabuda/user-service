package com.wszib.userservice.adapter.out.persistance.jpa;

import com.wszib.userservice.adapter.out.persistance.jpa.mapping.UserMappingFactory;
import com.wszib.userservice.adapter.out.persistance.jpa.model.UserData;
import com.wszib.userservice.domain.error.NullException;
import com.wszib.userservice.domain.querry.FilterCriteria;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.application.ports.out.UserRepository;
import com.wszib.userservice.infrastructure.adapter.DrivenAdapter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Transactional
@DrivenAdapter
class UserJpaRepository implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserJpaRepository.class);

    Function<UserData, User> userDataToDomainMapper;
    Function<User, UserData> userDomainToDataMapper;

    private final UserPersistenceInterface users;

    @Autowired
    UserJpaRepository(UserPersistenceInterface users){
        this(users,
                UserMappingFactory.createUserDataToDomainMapper(),
                UserMappingFactory.createUserDomainToDataMapper()
        );
    }

    UserJpaRepository(UserPersistenceInterface users,
                      Function<UserData, User> userDataToDomainMapper,
                      Function<User, UserData> userDomainToDataMapper){
        this.users = users;
        this.userDataToDomainMapper = userDataToDomainMapper;
        this.userDomainToDataMapper = userDomainToDataMapper;
    }


    @Override
    public Optional<User> findById(String id) {
        LOGGER.info("Finding user by id: {}", id);
        return users.findById(id).map(userDataToDomainMapper);
    }

    @Override
    public List<User> findAllBy(FilterCriteria criteria) {
        if(criteria == null) throw NullException.forFilterCriteria();
        LOGGER.info("Finding users by criteria: {}", criteria);
        return users.findAllBy(criteria).stream().map(userDataToDomainMapper).collect(Collectors.toList());
    }

    @Override
    public void save(User user) {
        if(user == null) throw new IllegalArgumentException("User cannot be null");
        LOGGER.info("Saving user: {}", user);
        users.save(userDomainToDataMapper.apply(user));
        LOGGER.info("Successfully created user");
    }

    @Override
    public void delete(String id) {
        LOGGER.info("Deleting user by id: {}", id);
        users.delete(id);
        LOGGER.info("Successfully deleted user");
    }

    @Override
    public void changeUserDetails(User user) {
        //TODO: Implement
    }
}
