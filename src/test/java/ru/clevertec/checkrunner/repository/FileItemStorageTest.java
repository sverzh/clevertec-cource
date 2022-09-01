package ru.clevertec.checkrunner.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.checkrunner.repository.filestorage.FileItemStorage;
import ru.clevertec.checkrunner.repository.filestorage.MapStorage;

class FileItemStorageTest {
    String itemListFilename  = "itemlist.txt";
    FileItemStorage fileStorage = new FileItemStorage(itemListFilename);

    @Test
    void initItemStorage() {
        MapStorage mapStorage = fileStorage.initItemStorage();
        Assertions.assertNotEquals(mapStorage,null);
    }
}