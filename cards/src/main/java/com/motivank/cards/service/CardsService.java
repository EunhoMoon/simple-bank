package com.motivank.cards.service;

import com.motivank.cards.dto.CardsDto;

public interface CardsService {

    void createCard(String mobileNumber);

    CardsDto getCard(String mobileNumber);

    boolean updateCard(CardsDto cardsDto);

    boolean deleteCard(String mobileNumber);

}
