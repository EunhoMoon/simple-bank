package com.motivank.loans.service;

import com.motivank.loans.dto.LoansDto;

public interface LoansService {

    void createLoan(String mobileNumber);

    LoansDto getLoan(String mobileNumber);

    boolean updateLoan(LoansDto loansDto);

    boolean deleteLoan(String mobileNumber);
    
}
