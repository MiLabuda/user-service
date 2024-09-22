package com.wszib.userservice.adapter.in.web.rest.model;

import lombok.Builder;

@Builder
public record ChangeUserDetailsDTO(
        String firstName,
        String lastName,
        String email
) {
}
