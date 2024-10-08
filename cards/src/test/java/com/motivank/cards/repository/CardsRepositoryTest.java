package com.motivank.cards.repository;

import com.motivank.cards.entity.Cards;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class CardsRepositoryTest {

    @Autowired
    CardsRepository cardsRepository;

    @Test
    @DisplayName("mobileNumber로 카드 조회시 정상 조회가 가능하다.")
    void findByMobileNumber() {
        // given
        String mobileNumber = "1231234123";
        Cards newCard = Cards.issuance(mobileNumber);
        cardsRepository.save(newCard);

        // when
        var cards = cardsRepository.findByMobileNumber(mobileNumber);

        // then
        assertThat(cards).isPresent();
    }

}