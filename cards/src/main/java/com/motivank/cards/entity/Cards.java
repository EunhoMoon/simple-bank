package com.motivank.cards.entity;

import com.motivank.cards.dto.CardsDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

import static com.motivank.cards.constants.CardsConstants.CREDIT_CARD;
import static com.motivank.cards.constants.CardsConstants.NEW_CARD_LIMIT;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cards extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "total_limit")
    private int totalLimit;

    @Column(name = "amount_used")
    private int amountUsed;

    @Column(name = "available_amount")
    private int availableAmount;

    @Builder
    private Cards(
        String mobileNumber,
        String cardNumber,
        String cardType,
        int totalLimit,
        int amountUsed,
        int availableAmount
    ) {
        this.mobileNumber = mobileNumber;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.totalLimit = totalLimit;
        this.amountUsed = amountUsed;
        this.availableAmount = availableAmount;
    }

    public static Cards issuance(String mobileNumber) {
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        return Cards.builder()
            .mobileNumber(mobileNumber)
            .cardNumber(Long.toString(randomCardNumber))
            .cardType(CREDIT_CARD)
            .totalLimit(NEW_CARD_LIMIT)
            .amountUsed(0)
            .availableAmount(NEW_CARD_LIMIT)
            .build();
    }

    public void update(CardsDto cardsDto) {
        this.totalLimit = cardsDto.getTotalLimit();
        this.amountUsed = cardsDto.getAmountUsed();
        this.availableAmount = cardsDto.getAvailableAmount();
    }
}
