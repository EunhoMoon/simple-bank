package com.motivank.accounts.dto;

import com.motivank.accounts.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerDto {

    private String name;

    private String email;

    private String mobileNumber;

    public Customer toEntity(CustomerDto customerDto, String createdBy) {
        return Customer.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .mobileNumber(customerDto.getMobileNumber())
                .createdBy(createdBy)
                .build();
    }

}
