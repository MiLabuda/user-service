package com.wszib.userservice.application.service;

import com.wszib.userservice.domain.error.NullFilterCriteriaException;
import com.wszib.userservice.domain.error.NullIdException;
import com.wszib.userservice.domain.querry.FilterCriteria;
import com.wszib.userservice.application.usecase.GetUserUseCase;
import com.wszib.userservice.domain.User;
import com.wszib.userservice.domain.UserNotFoundException;
import com.wszib.userservice.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
class GetUserService implements GetUserUseCase {

    private static final Logger LOGGER = LogManager.getLogger(GetUserService.class);

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