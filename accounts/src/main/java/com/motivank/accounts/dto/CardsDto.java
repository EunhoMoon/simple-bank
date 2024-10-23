package com.motivank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(name = "Cards", description = "Schema to hold Card information")
public class CardsDto {
    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be a 10-digit number")
    @Schema(description = "Mobile Number of Customer", example = "4354437687")
    private final String mobileNumber;

    @NotEmpty(message = "Card number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "CardNumber must be 12 digits")
    @Schema(description = "Card Number", example = "123456789012")
    private final String cardNumber;

    @NotEmpty(message = "Card type cannot be null or empty")
    @Schema(description = "Type of Card", example = "Credit Card")
    private final String cardType;

    @Positive(message = "Total card limit should be greater than zero")
    @Schema(description = "Total Limit of Card", example = "10000")
    private final int totalLimit;

    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    @Schema(description = "Amount Used", example = "5000")
    private final int amountUsed;

    @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    @Schema(description = "Available Amount", example = "5000")
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

}
