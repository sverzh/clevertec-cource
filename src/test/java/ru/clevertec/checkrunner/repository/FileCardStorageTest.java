package ru.clevertec.checkrunner.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.checkrunner.repository.filestorage.FileCardStorage;

class FileCardStorageTest {
    String cardListFileName = "cardlist.txt";
    FileCardStorage cardStorage = new FileCardStorage(cardListFileName);

    @Test
    void initCardList() {
        Assertions.assertTrue(cardStorage.initCardList());
    }

    @Test
    void notInitCardList() {
        String cardListFileName = "cardnumberlist.txt";
        cardStorage = new FileCardStorage(cardListFileName);
        Assertions.assertFalse(cardStorage.initCardList());
    }

    @Test
    void checkCard() {
        int cardNumber = 1004;
        cardStorage.initCardList();
        Assertions.assertTrue(FileCardStorage.checkCard(cardNumber));
    }
}