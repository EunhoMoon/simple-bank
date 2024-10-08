package com.motivank.cards.dto;

import com.motivank.cards.entity.Cards;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CardsDto {

    private final String mobileNumber;

    private final String cardNumber;

    private final String cardType;

    private final int totalLimit;

    private final int amountUsed;

    private final int availableAmount;

    @Builder
    private CardsDto(
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

    public static CardsDto of(Cards cards) {
        return CardsDto.builder()
                .mobileNumber(cards.getMobileNumber())
                .cardNumber(cards.getCardNumber())
                .cardType(cards.getCardType())
                .totalLimit(cards.getTotalLimit())
                .amountUsed(cards.getAmountUsed())
                .availableAmount(cards.getAvailableAmount())
                .build();
    }
}
