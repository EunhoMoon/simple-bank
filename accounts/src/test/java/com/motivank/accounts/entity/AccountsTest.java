package com.motivank.accounts.entity;

import com.motivank.accounts.constants.AccountsConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccountsTest {

    @Test
    @DisplayName("계좌 생성 시 생성 일자가 정상적으로 입력되는지 확인")
    void createdAtTest() {
        // given
        Accounts accounts = Accounts.builder()
                .customerId(1L)
                .accountNumber(1234567890L)
                .accountType(AccountsConstants.SAVINGS)
                .branchAddress(AccountsConstants.ADDRESS)
                .createdBy("Anonymous")
                .build();

        // when
        LocalDateTime createdAt = accounts.getCreatedAt();

        // then
        assertNotNull(createdAt);
    }

}