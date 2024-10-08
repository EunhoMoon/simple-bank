package com.motivank.loans.entity;

import com.motivank.loans.constants.LoansConstants;
import com.motivank.loans.dto.LoansDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

import static com.motivank.loans.constants.LoansConstants.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Loans extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "loan_number")
    private String loanNumber;

    @Column(name = "loan_type")
    private String loanType;

    @Column(name = "total_loan")
    private int totalLoan;

    @Column(name = "amount_paid")
    private int amountPaid;

    @Column(name = "outstanding_amount")
    private int outstandingAmount;

    @Builder
    private Loans(
            String mobileNumber,
            String loanNumber,
            String loanType,
            int totalLoan,
            int amountPaid,
            int outstandingAmount
    ) {
        this.mobileNumber = mobileNumber;
        this.loanNumber = loanNumber;
        this.loanType = loanType;
        this.totalLoan = totalLoan;
        this.amountPaid = amountPaid;
        this.outstandingAmount = outstandingAmount;
    }

    public static Loans issuance(String mobileNumber) {
        return Loans.builder()
                .mobileNumber(mobileNumber)
                .loanNumber(String.valueOf(100000000000L + new Random().nextInt(900000000)))
                .loanType(HOME_LOAN)
                .totalLoan(NEW_LOAN_LIMIT)
                .amountPaid(0)
                .outstandingAmount(NEW_LOAN_LIMIT)
                .build();
    }

    public void update(LoansDto loansDto) {
        this.amountPaid = loansDto.getAmountPaid();
        this.outstandingAmount = loansDto.getOutstandingAmount();
    }

}
