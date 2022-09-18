package ru.clevertec.checkrunner.repository.springdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checkrunner.model.Card;

@Repository
public interface CardStorageDataJpa extends JpaRepository<Card, Integer> {

    Page<Card> findAll(Pageable pageable);

    Card findByNumber(int number);

    void deleteByNumber(int number);
}
