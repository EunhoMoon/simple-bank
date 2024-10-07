package com.motivank.accounts.dto;

import com.motivank.accounts.entity.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CustomerDto {

    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 30, message="The length of the name should be between 5 and 30")
    private String name;

    @NotEmpty(message = "Email address cannot be null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be a 10-digit number")
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

    public Customer toEntity() {
        return Customer.builder()
                .name(this.name)
                .email(this.email)
                .mobileNumber(this.mobileNumber)
                .build();
    }

    public void setAccounts(AccountsDto accountsDto) {
        this.accountsDto = accountsDto;
    }
}
