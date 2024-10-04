package com.motivank.accounts.service.impl;

import com.motivank.accounts.constants.AccountsConstants;
import com.motivank.accounts.dto.CustomerDto;
import com.motivank.accounts.entity.Accounts;
import com.motivank.accounts.entity.Customer;
import com.motivank.accounts.exception.CustomerAlreadyExistsException;
import com.motivank.accounts.mapper.CustomerMapper;
import com.motivank.accounts.repository.AccountsRepository;
import com.motivank.accounts.repository.CustomerRepository;
import com.motivank.accounts.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountsService {

    private final AccountsRepository accountsRepository;

    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        checkCustomerAlreadyExists(customerDto);
        var customer = CustomerMapper.toEntity(customerDto, "Anonymous");
        var savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private void checkCustomerAlreadyExists(CustomerDto customerDto) {
        customerRepository.findByMobileNumber(customerDto.getMobileNumber())
                .ifPresent(customer -> {
                    throw new CustomerAlreadyExistsException(
                            "Customer already registered with given mobile number: " + customerDto.getMobileNumber()
                    );
                });
    }

    private Accounts createNewAccount(Customer customer) {
        long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);

        return Accounts.builder()
                .customerId(customer.getCustomerId())
                .accountNumber(randomAccountNumber)
                .accountType(AccountsConstants.SAVINGS)
                .branchAddress(AccountsConstants.ADDRESS)
                .createdBy("Anonymous")
                .build();
    }
}
