package com.motivank.loans.service.impl;

import com.motivank.loans.dto.LoansDto;
import com.motivank.loans.entity.Loans;
import com.motivank.loans.exception.LoanAlreadyExistsException;
import com.motivank.loans.exception.ResourceNotFoundException;
import com.motivank.loans.repository.LoansRepository;
import com.motivank.loans.service.LoansService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoansServiceImpl implements LoansService {

    private final LoansRepository loansRepository;

    @Override
    @Transactional
    public void createLoan(String mobileNumber) {
        loansRepository.findByMobileNumber(mobileNumber)
                .ifPresent(loans -> {
                    throw new LoanAlreadyExistsException("Loan already exists for mobile number: " + mobileNumber);
                });
        var newLoan = Loans.issuance(mobileNumber);
        loansRepository.save(newLoan);
    }

    @Override
    public LoansDto getLoan(String mobileNumber) {
        return loansRepository.findByMobileNumber(mobileNumber)
                .map(LoansDto::of)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
    }

    @Override
    @Transactional
    public boolean updateLoan(LoansDto loansDto) {
        loansRepository.findByMobileNumber(loansDto.getMobileNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", loansDto.getMobileNumber()))
                .update(loansDto);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteLoan(String mobileNumber) {
        var findLoan = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
        loansRepository.delete(findLoan);

        return true;
    }

}
