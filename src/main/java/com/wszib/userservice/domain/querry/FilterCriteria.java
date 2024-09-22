package com.wszib.userservice.domain.querry;

import com.wszib.userservice.infrastructure.common.query.Query;

import java.util.Optional;

public record FilterCriteria(Optional<String> email,
                             Optional<String> firstName,
                             Optional<String> lastName,
                             Optional<Boolean> active) implements Query {
}
