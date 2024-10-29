package com.wszib.userservice.domain.querry;

import com.wszib.userservice.infrastructure.query.Query;

import java.util.Optional;

public record FilterCriteria(String email, String firstName, String lastName, Boolean active) implements Query {
}

