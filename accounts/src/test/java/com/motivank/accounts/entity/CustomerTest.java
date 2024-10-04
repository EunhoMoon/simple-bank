package com.motivank.accounts.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    @DisplayName("사용자 생성 시 생성 일자가 정상적으로 입력되는지 확인")
    void createdAtTest() {
        // given
        Customer customer = Customer.builder()
                .name("Tester")
                .email("test@co.kr")
                .mobileNumber("010-1234-5678")
                .createdBy("Anonymous")
                .build();

        // when
        LocalDateTime createdAt = customer.getCreatedAt();

        // then
        assertNotNull(createdAt);
    }

}