package com.motivank.accounts.service.impl;

import com.motivank.accounts.constants.AccountsConstants;
import com.motivank.accounts.dto.CustomerDto;
import com.motivank.accounts.entity.Accounts;
import com.motivank.accounts.entity.Customer;
import com.motivank.accounts.exception.CustomerAlreadyExistsException;
import com.motivank.accounts.exception.ResourceNotFoundException;
import com.motivank.accounts.repository.AccountsRepository;
import com.motivank.accounts.repository.CustomerRepository;
import com.motivank.accounts.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountsServiceImpl implements AccountsService {

    private final AccountsRepository accountsRepository;

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public void createAccount(CustomerDto customerDto) {
        customerRepository.findByMobileNumber(customerDto.getMobileNumber())
                .ifPresent(customer -> {
                    throw new CustomerAlreadyExistsException(
                            "Customer already registered with given mobile number: " + customerDto.getMobileNumber()
                    );
                });

        var customer = customerDto.toEntity("Anonymous");
        var savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {
        var findCustomer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer",
                        "mobileNumber",
                        mobileNumber
                ));

        var findAccount = accountsRepository.findByCustomerId(findCustomer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account",
                        "customerId",
                        findCustomer.getCustomerId().toString()
                ));

        var findCustomerDto = findCustomer.toDto();
        findCustomerDto.setAccounts(findAccount.toDto());

        return findCustomerDto;
    }

    @Override
    @Transactional
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;

        var accountsDto = customerDto.getAccountsDto();

        if (accountsDto != null) {
            var findAccount = accountsRepository.findById(accountsDto.getAccountNumer())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Account",
                            "accountNumber",
                            accountsDto.getAccountNumer().toString()
                    ));
            findAccount.updateDetails(accountsDto);

            var customerId = findAccount.getCustomerId();
            var findCustomer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Customer",
                            "customerId",
                            customerId.toString()
                    ));
            findCustomer.updateDetails(customerDto);
            isUpdated = true;
        }

        return isUpdated;
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
