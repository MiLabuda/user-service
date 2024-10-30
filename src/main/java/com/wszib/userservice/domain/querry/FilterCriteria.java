package com.wszib.userservice.domain.querry;

import com.wszib.userservice.infrastructure.query.Query;

public record FilterCriteria(String email, String firstName, String lastName, Boolean enabled) implements Query {
}

