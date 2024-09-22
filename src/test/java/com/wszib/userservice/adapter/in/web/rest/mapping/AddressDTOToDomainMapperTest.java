package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.AddressDTO;
import com.wszib.userservice.domain.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AddressDTO to Domain Mapper Test")
class AddressDTOToDomainMapperTest {

    private final Function<AddressDTO, Address> mapper;

    AddressDTOToDomainMapperTest() {
        mapper = new AddressDTOToDomainMapper();
    }

    @Test
    @DisplayName("Should map null to literal null")
    void testNull() {
        Address result = mapper.apply(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should map AddressDTO to Address domain")
    void testValidMapping() {
        AddressDTO input = constructInput();
        Address expected = constructExpectedDomain();

        Address result = mapper.apply(input);
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    private Address constructExpectedDomain() {
        return Address.builder()
                .name("Home address")
                .street("Baker Street 221B")
                .city("London")
                .zipCode("NW1 6XE")
                .country("United Kingdom")
                .build();
    }

    private AddressDTO constructInput(){
        return AddressDTO.builder()
                .addressName("Home address")
                .street("Baker Street 221B")
                .city("London")
                .zipCode("NW1 6XE")
                .country("United Kingdom")
                .build();



    }
}
