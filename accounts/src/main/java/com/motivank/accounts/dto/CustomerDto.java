package com.motivank.accounts.dto;

import com.motivank.accounts.entity.Customer;
import lombok.Getter;

@Getter
public class CustomerDto {

    private String name;

    private String email;

    private String mobileNumber;

    private AccountsDto accountsDto;

    public CustomerDto(
            String name,
            String email,
            String mobileNumber
    ) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public Customer toEntity(String createdBy) {
        return Customer.builder()
                .name(this.name)
                .email(this.email)
                .mobileNumber(this.mobileNumber)
                .createdBy(createdBy)
                .build();
    }

    public void setAccounts(AccountsDto accountsDto) {
        this.accountsDto = accountsDto;
    }
}
