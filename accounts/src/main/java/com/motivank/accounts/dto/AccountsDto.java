package com.motivank.accounts.dto;

import com.motivank.accounts.entity.Accounts;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountsDto {

    @NotEmpty(message = "Account number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number should be a 10-digit number")
    private Long accountNumber;

    @NotEmpty(message = "Account type cannot be null or empty")
    private String accountType;

    @NotEmpty(message = "Branch address cannot be null or empty")
    private String branchAddress;

    public Accounts toEntity(Long customerId) {
        return Accounts.builder()
                .customerId(customerId)
                .accountNumber(this.accountNumber)
                .accountType(this.accountType)
                .branchAddress(this.branchAddress)
                .build();
    }

}
