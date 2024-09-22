package com.wszib.userservice.adapter.in.web.rest.mapping;

import com.wszib.userservice.adapter.in.web.rest.model.AddressDTO;
import com.wszib.userservice.domain.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Address Domain to DTO Mapper")
class AddressDomainToDTOMapperTest {

    Function<Address, AddressDTO> mapper;

    AddressDomainToDTOMapperTest() {
        mapper = new AddressDomainToDTOMapper();
    }

    @Test
    @DisplayName("Should map null to literal null")
    void testNull() {
        AddressDTO result = mapper.apply(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should map Address to Address DTO")
    void testValidMapping() {
        Address input = constructInput();
        AddressDTO expected = constructExpectedDomain();

        AddressDTO result = mapper.apply(input);
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    private Address constructInput() {
        return Address.builder()
                .name("Home address")
                .street("Baker Street 221B")
                .city("London")
                .zipCode("NW1 6XE")
                .country("United Kingdom")
                .build();
    }

    private AddressDTO constructExpectedDomain(){
        return AddressDTO.builder()
                .addressName("Home address")
                .street("Baker Street 221B")
                .city("London")
                .zipCode("NW1 6XE")
                .country("United Kingdom")
                .build();



    }

}
