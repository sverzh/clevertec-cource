package ru.clevertec.checkrunner.servlets;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.model.Card;
import ru.clevertec.checkrunner.repository.springdata.CardStorageDataJpa;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardStorageDataJpa cardStorageDataJpa;

    public Page<Card> findAll(String numberPage, String pageSize) {
        int size = pageSize != null ? Integer.parseInt(pageSize) : 20;
        int page = numberPage != null ? (Integer.parseInt(numberPage) * size) : 0;
        return cardStorageDataJpa.findAll(PageRequest.of(page, size));
    }

    public Card findByNumber(int number) {
        return cardStorageDataJpa.findByNumber(number);
    }

    public Card save(Card card) {
        return cardStorageDataJpa.save(card);
    }

    public void delete(int number) {
        cardStorageDataJpa.deleteByNumber(number);
    }

}
