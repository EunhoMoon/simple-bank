package com.motivank.accounts.dto;

import com.motivank.accounts.entity.Accounts;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountsDto {

    private Long accountNumer;

    private String accountType;

    private String branchAddress;

    public Accounts toEntity(AccountsDto accountsDto) {
        return Accounts.builder()
                .accountNumber(accountsDto.getAccountNumer())
                .accountType(accountsDto.getAccountType())
                .branchAddress(accountsDto.getBranchAddress())
                .build();
    }

}
