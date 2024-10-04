package com.motivank.accounts.entity;

import com.motivank.accounts.dto.AccountsDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accounts extends BaseEntity {

    @Column(name = "customer_id")
    private Long customerId;

    @Id
    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "branch_address")
    private String branchAddress;

    @Builder
    private Accounts(
            Long customerId,
            Long accountNumber,
            String accountType,
            String branchAddress,
            String createdBy
    ) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.branchAddress = branchAddress;
        this.setCreatedBy(createdBy);
        this.setCreatedAt(LocalDateTime.now());
    }

    public AccountsDto toDto() {
        return new AccountsDto(
                this.accountNumber,
                this.accountType,
                this.branchAddress
        );
    }

}
