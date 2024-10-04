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

    public Accounts toEntity() {
        return Accounts.builder()
                .accountNumber(this.accountNumer)
                .accountType(this.accountType)
                .branchAddress(this.branchAddress)
                .build();
    }

}
