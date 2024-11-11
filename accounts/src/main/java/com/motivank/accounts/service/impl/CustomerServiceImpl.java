package com.motivank.accounts.service.impl;

import com.motivank.accounts.dto.CardsDto;
import com.motivank.accounts.dto.CustomerDetailsDto;
import com.motivank.accounts.dto.LoansDto;
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
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
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


        CardsDto cardsDto = null;
        LoansDto loansDto = null;

        var cardsResponse = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        var loansResponse = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);

        if (cardsResponse != null) cardsDto = cardsResponse.getBody();
        if (loansResponse != null) loansDto = loansResponse.getBody();

        return CustomerDetailsDto.of(customer.toDto(), accounts.toDto(), cardsDto, loansDto);
    }

}
