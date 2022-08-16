package ru.clevertec.checkrunner.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.filestorage.MapStorage;

class MapStorageTest {
    MapStorage mapStorage = new MapStorage();
    final Item ITEM1 = new Item(1, "Chocolate", 5, false);
    final Item ITEM2 = new Item(2, "Chese", 2, false);

    @BeforeAll
    static void fillStorage(){
        MapStorage mapStorage = new MapStorage();
        Item item1 = new Item(1, "Chocolate", 5, false);
        mapStorage.add(item1);
    }

    @Test
    void get() {
        Assertions.assertEquals(mapStorage.get(1), ITEM1);
    }

    @Test
    void save() {
        mapStorage.add(ITEM2);
        Assertions.assertEquals(mapStorage.get(2), ITEM2);
    }
}