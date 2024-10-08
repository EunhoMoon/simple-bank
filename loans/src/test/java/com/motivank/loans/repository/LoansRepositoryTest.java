package com.motivank.loans.repository;

import com.motivank.loans.entity.Loans;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class LoansRepositoryTest {

    @Autowired
    LoansRepository loansRepository;

    @Test
    @DisplayName("mobileNumber로 대출정보 조회 성공")
    void createLoanTest() {
        // given
        String mobileNumber = "010-1234-5678";
        Loans newLoan = Loans.issuance(mobileNumber);
        loansRepository.save(newLoan);

        // when
        Optional<Loans> findLoan = loansRepository.findByMobileNumber(mobileNumber);

        // then
        assertThat(findLoan).isPresent();
    }

}