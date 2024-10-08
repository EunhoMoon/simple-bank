package com.motivank.cards.service.impl;

import com.motivank.cards.dto.CardsDto;
import com.motivank.cards.exception.CardAlreadyExistsException;
import com.motivank.cards.exception.ResourceNotFoundException;
import com.motivank.cards.repository.CardsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
@DisplayName("CardsService 테스트")
class CardsServiceImplTest {

    @Autowired
    CardsServiceImpl cardsService;

    @Autowired
    CardsRepository cardsRepository;

    @AfterEach
    void tearDown() {
        cardsRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("mobileNumber를 사용한 카드 발급이 성공한다.")
    void createCard() {
        // given
        String mobileNumber = "1231234123";

        // when
        cardsService.createCard(mobileNumber);

        // then
        assertThat(cardsRepository.findByMobileNumber(mobileNumber)).isPresent();
    }

    @Test
    @DisplayName("이미 존재하는 mobileNumber로 카드 발급 시 CardAlreadyExistsException이 발생한다.")
    void createCard_CardAlreadyExistsException() {
        // given
        String mobileNumber = "1231234123";
        cardsService.createCard(mobileNumber);

        // expect
        assertThatThrownBy(() -> cardsService.createCard(mobileNumber))
                .isInstanceOf(CardAlreadyExistsException.class);
    }

    @Test
    @DisplayName("mobileNumber로 카드 조회시 카드 정보가 없다면 ResourceNotFoundException이 발생한다.")
    void getCard_ResourceNotFoundException() {
        // given
        String mobileNumber = "1231234123";

        // expect
        assertThatThrownBy(() -> cardsService.getCard(mobileNumber))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("mobileNumber로 카드 조회가 성공한다.")
    void getCard() {
        // given
        String mobileNumber = "1231234123";
        cardsService.createCard(mobileNumber);

        // when
        var card = cardsService.getCard(mobileNumber);

        // then
        assertThat(card.getMobileNumber()).isEqualTo(mobileNumber);
    }

    @Test
    @DisplayName("카드 정보를 수정한다.")
    void updateCard() {
        // given
        String mobileNumber = "1231234123";
        cardsService.createCard(mobileNumber);
        var card = cardsService.getCard(mobileNumber);

        // when
        CardsDto updateDetails = CardsDto.builder()
                .mobileNumber(mobileNumber)
                .cardNumber(card.getCardNumber())
                .cardType(card.getCardType())
                .totalLimit(card.getTotalLimit())
                .amountUsed(1000)
                .availableAmount(card.getAvailableAmount())
                .build();
        cardsService.updateCard(updateDetails);

        // then
        var updatedCard = cardsService.getCard(mobileNumber);
        assertThat(updatedCard.getAmountUsed()).isEqualTo(1000);
    }

    @Test
    @DisplayName("카드 정보 업데이트 시 mobileNumber로 조회된 카드가 없다면 ResourceNotFoundException이 발생한다.")
    void updateCard_ResourceNotFoundException() {
        // given
        String mobileNumber = "1231234123";
        CardsDto updateDetails = CardsDto.builder()
                .mobileNumber(mobileNumber)
                .cardNumber("123412341234")
                .cardType("CREDIT_CARD")
                .totalLimit(10000)
                .amountUsed(1000)
                .availableAmount(9000)
                .build();

        // expect
        assertThatThrownBy(() -> cardsService.updateCard(updateDetails))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("카드 정보를 삭제한다.")
    void deleteCard() {
        // given
        String mobileNumber = "1231234123";
        cardsService.createCard(mobileNumber);

        // when
        cardsService.deleteCard(mobileNumber);

        // then
        assertThat(cardsRepository.findByMobileNumber(mobileNumber)).isEmpty();
    }

    @Test
    @DisplayName("카드 정보 삭제 시 mobileNumber로 조회된 카드가 없다면 ResourceNotFoundException이 발생한다.")
    void deleteCard_ResourceNotFoundException() {
        // given
        String mobileNumber = "1231234123";

        // expect
        assertThatThrownBy(() -> cardsService.deleteCard(mobileNumber))
                .isInstanceOf(ResourceNotFoundException.class);
    }

}