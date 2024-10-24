package com.motivank.accounts.service.impl;

import com.motivank.accounts.dto.CustomerDetailsDto;
import com.motivank.accounts.exception.ResourceNotFoundException;
import com.motivank.accounts.repository.AccountsRepository;
import com.motivank.accounts.repository.CustomerRepository;
import com.motivank.accounts.service.CustomerService;
import com.motivank.accounts.service.client.CardsFeignClient;
import com.motivank.accounts.service.client.LoansFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final AccountsRepository accountsRepository;

    private final CustomerRepository customerRepository;

    private final CardsFeignClient cardsFeignClient;

    private final LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        var customer = customerRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Customer",
                "mobileNumber",
                mobileNumber
            ));
        var accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
            .orElseThrow(() -> new ResourceNotFoundException(
                "Account",
                "customerId",
                customer.getCustomerId().toString()
            ));
        var cardsDto = cardsFeignClient.fetchCardDetails(mobileNumber).getBody();
        var loansDto = loansFeignClient.fetchLoanDetails(mobileNumber).getBody();

        return CustomerDetailsDto.of(customer.toDto(), accounts.toDto(), cardsDto, loansDto);
    }

}
