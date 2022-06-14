package ru.clevertec.checkrunner.util;

import org.junit.jupiter.api.Test;
import ru.clevertec.checkrunner.repository.FileCardStorage;
import ru.clevertec.checkrunner.repository.FileItemStorage;

import static org.junit.jupiter.api.Assertions.*;

class PrintReceiptTest {
    String pathString = "itemlist.txt cardlist.txt 3-1 2-5 5-1 card-1234";
    String[] parsedString = pathString.split(" ");
    StringParser stringParser = new StringParser(parsedString);
    FileItemStorage itemStorage = new FileItemStorage(parsedString[0]);
    PrintReceipt printReceipt = new PrintReceipt(stringParser.getParsedMap(), stringParser.getCardNumber(), itemStorage.initItemStorage());

    @Test
    void printReceiptToConsole() {
        printReceipt.printReceiptToConsole();

    }

    @Test
    void printReceiptToFile() throws Exception{
        printReceipt.printReceiptToFile();
    }
}