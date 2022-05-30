package ru.clevertec.checkrunner.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileItemStorageTest {
    String itemListFilename  = "itemlist.txt";
    FileItemStorage fileStorage = new FileItemStorage(itemListFilename);

    @Test
    void initItemStorage() {
        MapStorage mapStorage = fileStorage.initItemStorage();
        Assertions.assertNotEquals(mapStorage,null);
    }
}