package com.motivank.cards.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardsTest {

    @Test
    @DisplayName("신규 카드 발급시 카드번호 생성")
    void createCard() {
        var mobileNumber = "1234567890";
        var card = Cards.issuance(mobileNumber);

        assertThat(card.getCardNumber()).isNotNull();
    }

}