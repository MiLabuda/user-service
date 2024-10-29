package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.ChangeUserDetailsDTO;
import com.wszib.userservice.domain.Email;
import com.wszib.userservice.domain.FirstName;
import com.wszib.userservice.domain.LastName;
import com.wszib.userservice.domain.UserId;
import com.wszib.userservice.domain.command.ChangeUserDetailsCommand;
import com.wszib.userservice.domain.error.NullException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ChangeUserDTO To Command Mapper")
class ChangeUserDTOToCommandMapperTest {

    Function<ChangeUserDetailsDTO, ChangeUserDetailsCommand> mapper;

    @Test
    @DisplayName("Should map null to literal null")
    void testNull() {
        UserId userId = UserId.generate();
        mapper = new ChangeUserDetailsDTOToCommandMapper(userId.toString());
        ChangeUserDetailsCommand result = mapper.apply(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should not map DTO with null UserId")
    void testNullUserId() {
        mapper = new ChangeUserDetailsDTOToCommandMapper(null);
        ChangeUserDetailsDTO input = constructInput();
        assertThrows(NullException.class, () -> mapper.apply(input));
    }

    @Test
    @DisplayName("Should map ChangeUserDetailsDTO to ChangeUserDetailsCommand")
    void testValidMapping() {
        UserId userId = UserId.generate();
        mapper = new ChangeUserDetailsDTOToCommandMapper(userId.value());
        ChangeUserDetailsDTO input = constructInput();
        ChangeUserDetailsCommand expected = constructExpectedCommand(userId);

        ChangeUserDetailsCommand result = mapper.apply(input);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "registeredAt")
                .isEqualTo(expected);
    }

    private ChangeUserDetailsDTO constructInput() {
        return ChangeUserDetailsDTO.builder()
                .firstName("Sherlock")
                .lastName("Holmes")
                .email("sherlock.holmes@gmail.com")
                .build();
    }

    private ChangeUserDetailsCommand constructExpectedCommand(UserId userId) {
        return new ChangeUserDetailsCommand.Builder()
                .userId(userId)
                .firstName(FirstName.of("Sherlock"))
                .lastName(LastName.of("Holmes"))
                .email(Email.of("sherlock.holmes@gmail.com"))
                .build();
    }
}
