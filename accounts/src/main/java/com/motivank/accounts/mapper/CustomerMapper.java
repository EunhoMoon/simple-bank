package com.motivank.accounts.mapper;

import com.motivank.accounts.dto.CustomerDto;
import com.motivank.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDto toDto(Customer customer) {
        return new CustomerDto(
            customer.getName(),
            customer.getEmail(),
            customer.getMobileNumber()
        );
    }

    public static Customer toEntity(CustomerDto customerDto, String createdBy) {
        return Customer.builder()
            .name(customerDto.getName())
            .email(customerDto.getEmail())
            .mobileNumber(customerDto.getMobileNumber())
            .createdBy(createdBy)
            .build();
    }

}
