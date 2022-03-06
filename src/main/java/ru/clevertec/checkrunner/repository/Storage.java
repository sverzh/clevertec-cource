package ru.clevertec.checkrunner.repository;

import ru.clevertec.checkrunner.model.Item;

public interface Storage {
    Item get(int id);

    void save(Item item);
}