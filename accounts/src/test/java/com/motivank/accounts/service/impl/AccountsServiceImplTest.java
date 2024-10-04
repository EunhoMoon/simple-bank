package com.motivank.accounts.service.impl;

import com.motivank.accounts.dto.CustomerDto;
import com.motivank.accounts.entity.Accounts;
import com.motivank.accounts.entity.Customer;
import com.motivank.accounts.exception.CustomerAlreadyExistsException;
import com.motivank.accounts.repository.AccountsRepository;
import com.motivank.accounts.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AccountsServiceImplTest {

    @Autowired
    AccountsServiceImpl accountsService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @AfterEach
    void tearDown() {
        accountsRepository.deleteAllInBatch();
        customerRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("계좌 생성 시 customer 및 account 정보가 정상적으로 입력되는지 확인")
    void createAccountTest() {
        // given
        CustomerDto customerDto = new CustomerDto(
                "Tester",
                "test@co.kr",
                "010-1234-5678"
        );

        // when
        accountsService.createAccount(customerDto);

        // then
        Optional<Customer> findCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        assertTrue(findCustomer.isPresent());

        findCustomer.ifPresent(customer -> {
            assertNotNull(customer.getCustomerId());
            assertEquals(customerDto.getName(), customer.getName());
            assertEquals(customerDto.getEmail(), customer.getEmail());
            assertEquals(customerDto.getMobileNumber(), customer.getMobileNumber());
        });

        Optional<Accounts> findAccount = accountsRepository.findByCustomerId(findCustomer.get().getCustomerId());
        assertTrue(findAccount.isPresent());

        findAccount.ifPresent(account -> {
            assertNotNull(account.getAccountNumber());
            assertEquals(findCustomer.get().getCustomerId(), account.getCustomerId());
            assertNotNull(account.getAccountNumber());
        });
    }

    @Test
    @DisplayName("이미 등록된 사용자의 경우 CustomerAlreadyExistsException 발생")
    void createAccountWithAlreadyRegisteredCustomerTest() {
        // given
        CustomerDto customerDto = new CustomerDto(
                "Tester",
                "test@co.kr",
                "010-1234-5678"
        );
        accountsService.createAccount(customerDto);

        CustomerDto newCustomer = new CustomerDto(
                "Tester2",
                "test2@co.kr",
                "010-1234-5678"
        );

        // expect
        assertThrows(CustomerAlreadyExistsException.class, () -> accountsService.createAccount(newCustomer));
    }
}