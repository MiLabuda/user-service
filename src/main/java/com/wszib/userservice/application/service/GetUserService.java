package com.wszib.userservice.application.service;

import com.wszib.userservice.domain.error.NullFilterCriteriaException;
import com.wszib.userservice.domain.error.NullIdException;
import com.wszib.userservice.domain.querry.FilterCriteria;
import com.wszib.userservice.application.ports.in.GetUserUseCase;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.domain.UserNotFoundException;
import com.wszib.userservice.application.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
class GetUserService implements GetUserUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetUserService.class);

    private final UserRepository userRepository;

    @Override
    public List<User> findAllBy(FilterCriteria criteria) {
        if (criteria == null) throw new NullFilterCriteriaException();
        LOGGER.info("Retrieving customers...");
        return userRepository.findAllBy(criteria);
    }

    @Override
    public User findById(String id) {
        if(id == null) throw new NullIdException();
        LOGGER.info("Retrieving single customer...");
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
