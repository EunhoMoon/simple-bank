package com.motivank.accounts.dto;

import com.motivank.accounts.entity.Accounts;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountsDto {

    private Long accountNumber;

    private String accountType;

    private String branchAddress;

    public Accounts toEntity(Long customerId, String createdBy) {
        return Accounts.builder()
                .customerId(customerId)
                .accountNumber(this.accountNumber)
                .accountType(this.accountType)
                .branchAddress(this.branchAddress)
                .createdBy(createdBy)
                .build();
    }

}
