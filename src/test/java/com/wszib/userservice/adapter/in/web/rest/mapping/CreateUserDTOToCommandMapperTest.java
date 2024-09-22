package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.AddressDTO;
import com.wszib.userservice.adapter.in.web.rest.model.CreateUserDTO;
import com.wszib.userservice.domain.*;
import com.wszib.userservice.domain.command.CreateUserCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("CreateUserDTO to Command Mapper Test")
class CreateUserDTOToCommandMapperTest {

    Function<CreateUserDTO, CreateUserCommand> mapper;

    CreateUserDTOToCommandMapperTest() {
        mapper = new CreateUserDTOToCommandMapper();
    }

    @Test
    @DisplayName("Should map null to literal null")
    void testNull() {
        CreateUserCommand result = mapper.apply(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should map CreateUserDTO to CreateUserCommand")
    void testValidMapping() {
        CreateUserDTO input = constructInput();
        CreateUserCommand expected = constructExpectedCommand();

        CreateUserCommand result = mapper.apply(input);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "registeredAt")
                .isEqualTo(expected);
    }

    private CreateUserDTO constructInput() {
        return CreateUserDTO.builder()
                .firstName("Sherlock")
                .lastName("Holmes")
                .email("sherlock.holmes@gmail.com")
                .password("StrongPassword1!")
                .roleName("CUSTOMER")
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
    private CreateUserCommand constructExpectedCommand() {
        return new CreateUserCommand.Builder()
                .firstName(FirstName.of("Sherlock"))
                .lastName(LastName.of("Holmes"))
                .email(Email.of("sherlock.holmes@gmail.com"))
                .password(Password.of("StrongPassword1!"))
                .roleName(RoleName.of("CUSTOMER"))
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


}
