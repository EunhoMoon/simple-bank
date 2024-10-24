package com.motivank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Schema(
    name = "CustomerDetails",
    description = "Details(Customer, Account, Cards, Loans) of a customer"
)
public class CustomerDetailsDto {

    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 30")
    @Schema(
        description = "Name of the customer",
        example = "John Doe"
    )
    private final String name;

    @NotEmpty(message = "Email address cannot be null or empty")
    @Email(message = "Email address should be a valid value")
    @Schema(
        description = "Email address of the customer",
        example = "jd@test.com"
    )
    private final String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be a 10-digit number")
    @Schema(
        description = "Mobile number of the customer",
        example = "9876543210"
    )
    private final String mobileNumber;

    @Setter
    @Schema(
        description = "Accounts details of the customer"
    )
    private AccountsDto accountsDto;

    @Setter
    @Schema(
        description = "Cards details of the customer"
    )
    private CardsDto cardsDto;

    @Setter
    @Schema(
        description = "Loans details of the customer"
    )
    private LoansDto loansDto;

    @Builder
    private CustomerDetailsDto(
        String name,
        String email,
        String mobileNumber,
        AccountsDto accountsDto,
        CardsDto cardsDto,
        LoansDto loansDto
    ) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.accountsDto = accountsDto;
        this.cardsDto = cardsDto;
        this.loansDto = loansDto;
    }

    public static CustomerDetailsDto of(
        CustomerDto customerDto,
        AccountsDto accountsDto,
        CardsDto cardsDto,
        LoansDto loansDto
    ) {
        return CustomerDetailsDto.builder()
            .name(customerDto.getName())
            .email(customerDto.getEmail())
            .mobileNumber(customerDto.getMobileNumber())
            .accountsDto(accountsDto)
            .cardsDto(cardsDto)
            .loansDto(loansDto)
            .build();
    }

}
