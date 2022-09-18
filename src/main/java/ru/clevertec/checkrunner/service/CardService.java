package ru.clevertec.checkrunner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.checkrunner.repository.springdata.CardStorageDataJpa;


@Service
@RequiredArgsConstructor
public class CardService{

    private final CardStorageDataJpa cardStorageDataJpa;

    public Page<Card> findAll(final Pageable pageable) {
        return cardStorageDataJpa.findAll(pageable);
    }

    public Card findByNumber(final int number) {
        return this.cardStorageDataJpa.findByNumber(number);
    }

    public Card save(final Card card) {
        return cardStorageDataJpa.save(card);
    }

    public void delete(final int number) {
        this.cardStorageDataJpa.deleteByNumber(number);
    }

}