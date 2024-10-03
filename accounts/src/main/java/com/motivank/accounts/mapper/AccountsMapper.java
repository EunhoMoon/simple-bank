package com.motivank.accounts.mapper;

import com.motivank.accounts.dto.AccountsDto;
import com.motivank.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto toDto(Accounts accounts) {
        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumer(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static Accounts toEntity(AccountsDto accountsDto) {
        return Accounts.builder()
            .accountNumber(accountsDto.getAccountNumer())
            .accountType(accountsDto.getAccountType())
            .branchAddress(accountsDto.getBranchAddress())
            .build();
    }

}
