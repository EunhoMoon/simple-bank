package com.motivank.loans.service.impl;

import com.motivank.loans.dto.LoansDto;
import com.motivank.loans.entity.Loans;
import com.motivank.loans.exception.LoanAlreadyExistsException;
import com.motivank.loans.exception.ResourceNotFoundException;
import com.motivank.loans.repository.LoansRepository;
import com.motivank.loans.service.LoansService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class LoansServiceImplTest {

    @Autowired
    LoansService loansService;

    @Autowired
    LoansRepository loansRepository;

    @AfterEach
    void tearDown() {
        loansRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("대출 생성 시 대출 정보가 정상적으로 입력되는지 확인")
    void createLoanTest() {
        // given
        String mobileNumber = "1012345678";

        // when
        loansService.createLoan(mobileNumber);

        // then
        assertThat(loansRepository.findByMobileNumber(mobileNumber)).isPresent();
    }

    @Test
    @DisplayName("이미 존재하는 mobileNumber로 대출 생성 시 LoanAlreadyExistsException이 발생한다.")
    void createLoan_LoanAlreadyExistsException() {
        // given
        String mobileNumber = "1012345678";
        loansService.createLoan(mobileNumber);

        // expect
        assertThatThrownBy(() -> loansService.createLoan(mobileNumber))
                .isInstanceOf(LoanAlreadyExistsException.class);
    }

    @Test
    @DisplayName("mobileNumber로 대출 조회시 대출 정보가 없다면 ResourceNotFoundException이 발생한다.")
    void createLoan_ResourceNotFoundException() {
        // given
        String mobileNumber = "1012345678";

        // expect
        assertThatThrownBy(() -> loansService.getLoan(mobileNumber))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("mobileNumber로 대출 조회시 대출 정보가 있다면 정상 조회가 가능하다.")
    void getLoan() {
        // given
        String mobileNumber = "1012345678";
        loansService.createLoan(mobileNumber);

        // when
        var loans = loansService.getLoan(mobileNumber);

        // then
        assertThat(loans).isNotNull();
        assertThat(loans.getMobileNumber()).isEqualTo(mobileNumber);
    }

    @Test
    @DisplayName("대출 정보를 수정한다.")
    void updateLoan() {
        // given
        String mobileNumber = "1012345678";
        Loans loans = Loans.issuance(mobileNumber);
        loansRepository.save(loans);

        LoansDto updateLoanDetail = LoansDto.builder()
                .mobileNumber(loans.getMobileNumber())
                .loanNumber(loans.getLoanNumber())
                .loanType(loans.getLoanType())
                .totalLoan(loans.getTotalLoan())
                .amountPaid(120000)
                .outstandingAmount(88000)
                .build();

        // when
        loansService.updateLoan(updateLoanDetail);

        // then
        Optional<Loans> findLoan = loansRepository.findByMobileNumber(mobileNumber);
        assertThat(findLoan).isPresent();
        assertThat(findLoan.get().getAmountPaid()).isEqualTo(120000);
        assertThat(findLoan.get().getOutstandingAmount()).isEqualTo(88000);
    }

    @Test
    @DisplayName("대출 정보 수정시 대출 정보가 없다면 ResourceNotFoundException이 발생한다.")
    void updateLoan_ResourceNotFoundException() {
        // given
        LoansDto updateLoanDetail = LoansDto.builder()
                .mobileNumber("1012345678")
                .loanNumber("123456789012")
                .loanType("Home Loan")
                .totalLoan(100000)
                .amountPaid(120000)
                .outstandingAmount(88000)
                .build();

        // expect
        assertThatThrownBy(() -> loansService.updateLoan(updateLoanDetail))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("대출 정보 삭제시 대출 정보가 없다면 ResourceNotFoundException이 발생한다.")
    void deleteLoan_ResourceNotFoundException() {
        // given
        String mobileNumber = "1012345678";

        // expect
        assertThatThrownBy(() -> loansService.deleteLoan(mobileNumber))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("대출 정보 삭제시 대출 정보가 있다면 정상 삭제가 가능하다.")
    void deleteLoan() {
        // given
        String mobileNumber = "1012345678";
        loansService.createLoan(mobileNumber);

        // when
        loansService.deleteLoan(mobileNumber);

        // then
        assertThat(loansRepository.findByMobileNumber(mobileNumber)).isEmpty();
    }

}