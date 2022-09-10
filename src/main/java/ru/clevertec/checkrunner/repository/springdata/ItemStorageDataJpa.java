package ru.clevertec.checkrunner.repository.springdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checkrunner.model.Item;

import java.util.Optional;

@Repository
public interface ItemStorageDataJpa extends JpaRepository<Item, Integer> {

    Page<Item> findAll(Pageable pageable);

    Optional<Item> findByName(String name);
}

