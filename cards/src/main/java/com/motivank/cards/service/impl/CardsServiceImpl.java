package com.motivank.cards.service.impl;

import com.motivank.cards.dto.CardsDto;
import com.motivank.cards.repository.CardsRepository;
import com.motivank.cards.service.CardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardsServiceImpl implements CardsService {

    private final CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {

    }

    @Override
    public CardsDto getCard(String mobileNumber) {
        return null;
    }

    @Override
    public boolean updateCard(String mobileNumber, Long amount) {
        return false;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        return false;
    }

}
