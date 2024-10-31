package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.AddressDTO;
import com.wszib.userservice.adapter.in.web.rest.model.RegisterUserDTO;
import com.wszib.userservice.domain.*;
import com.wszib.userservice.domain.command.RegisterUserCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("RegisterUserDTO to Command Mapper Test")
class RegisterUserDTOToCommandMapperTest {

    Function<RegisterUserDTO, RegisterUserCommand> mapper;

    RegisterUserDTOToCommandMapperTest() {
        mapper = new CreateUserDTOToCommandMapper();
    }

    @Test
    @DisplayName("Should map null to literal null")
    void testNull() {
        RegisterUserCommand result = mapper.apply(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should map RegisterUserDTO to RegisterUserCommand")
    void testValidMapping() {
        RegisterUserDTO input = constructInput();
        RegisterUserCommand expected = constructExpectedCommand();

        RegisterUserCommand result = mapper.apply(input);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "registeredAt", "userId")
                .isEqualTo(expected);
    }

    private RegisterUserDTO constructInput() {
        return RegisterUserDTO.builder()
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
    private RegisterUserCommand constructExpectedCommand() {
        return new RegisterUserCommand.Builder()
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
