package com.wszib.userservice.adapter.in.web.rest.mapping;


import com.wszib.userservice.adapter.in.web.rest.model.AddressDTO;
import com.wszib.userservice.adapter.in.web.rest.model.UserDTO;
import com.wszib.userservice.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("User domain to DTO mapper")
class UserDomainToDTOMapperTest {

    Function<User, UserDTO> mapper;

    UserDomainToDTOMapperTest() {
        mapper = new UserDomainToDTOMapper();
    }

    @Test
    @DisplayName("Should map null to literal null")
    void testNull() {
        UserDTO result = mapper.apply(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should map User to UserDTO")
    void testValidMapping() {
        UserId userId = UserId.generate();
        User input = constructInput(userId);
        UserDTO expected = constructExpectedDTO(userId);

        UserDTO result = mapper.apply(input);
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    private User constructInput(UserId userId) {
        return User.builder()
                .id(userId)
                .firstName(FirstName.of("Sherlock"))
                .lastName(LastName.of("Holmes"))
                .email(Email.of("sherlock.holmes@gmail.com"))
                .roles(Set.of(UserRole.builder().roleName(RoleName.CUSTOMER).build()))
                .active(true)
                .invoiceAddress(Address.builder()
                        .name("Home address")
                        .street("Baker Street 221B")
                        .city("London")
                        .zipCode("NW1 6XE")
                        .country("United Kingdom")
                        .build())
                .deliveryAddress(Address.builder()
                        .name("Delivery address")
                        .street("Baker Street 223A")
                        .city("London")
                        .zipCode("NW1 6XE")
                        .country("United Kingdom")
                        .build())
                .build();
    }
    private UserDTO constructExpectedDTO(UserId userId) {
        return UserDTO.builder()
                .id(userId.value())
                .firstName("Sherlock")
                .lastName("Holmes")
                .email("sherlock.holmes@gmail.com")
                .roles(Set.of("CUSTOMER"))
                .active(true)
                .invoiceAddress(AddressDTO.builder()
                        .addressName("Home address")
                        .street("Baker Street 221B")
                        .city("London")
                        .zipCode("NW1 6XE")
                        .country("United Kingdom")
                        .build())
                .deliveryAddress(AddressDTO.builder()
                        .addressName("Delivery address")
                        .street("Baker Street 223A")
                        .city("London")
                        .zipCode("NW1 6XE")
                        .country("United Kingdom")
                        .build())
                .build();
    }
}
