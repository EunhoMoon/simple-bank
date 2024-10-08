package com.motivank.cards.service.impl;

import com.motivank.cards.dto.CardsDto;
import com.motivank.cards.entity.Cards;
import com.motivank.cards.exception.CardAlreadyExistsException;
import com.motivank.cards.exception.ResourceNotFoundException;
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
    @Transactional
    public void createCard(String mobileNumber) {
        cardsRepository.findByMobileNumber(mobileNumber)
                .ifPresent(cards -> {
                    throw new CardAlreadyExistsException("Card already exists for mobile number: " + mobileNumber);
                });
        var newCard = Cards.issuance(mobileNumber);
        cardsRepository.save(newCard);
    }

    @Override
    public CardsDto getCard(String mobileNumber) {
        return cardsRepository.findByMobileNumber(mobileNumber)
                .map(CardsDto::of)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
    }

    @Override
    @Transactional
    public boolean updateCard(CardsDto cardsDto) {
        cardsRepository.findByMobileNumber(cardsDto.getMobileNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", cardsDto.getMobileNumber()))
                .update(cardsDto);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteCard(String mobileNumber) {
        var findCardId = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber))
                .getCardId();
        cardsRepository.deleteById(findCardId);

        return true;
    }

}
