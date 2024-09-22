package com.wszib.userservice.application.usecase;

import com.wszib.userservice.domain.querry.FilterCriteria;
import com.wszib.userservice.domain.User;

import java.util.List;

public interface GetUserUseCase {

    List<User> findAllBy(FilterCriteria criteria);

    User findById(String id);
}
