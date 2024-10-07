package com.motivank.cards.service;

import com.motivank.cards.dto.CardsDto;

public interface CardsService {

    void createCard(String mobileNumber);

    CardsDto getCard(String mobileNumber);

    boolean updateCard(String mobileNumber, Long amount);

    boolean deleteCard(String mobileNumber);

}
