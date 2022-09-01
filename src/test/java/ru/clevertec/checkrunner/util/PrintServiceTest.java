package ru.clevertec.checkrunner.util;

import org.junit.jupiter.api.Test;
import ru.clevertec.checkrunner.repository.FileItemStorage;
import ru.clevertec.checkrunner.service.PrintService;
import ru.clevertec.checkrunner.service.PrintServiceInterface;
import ru.clevertec.checkrunner.service.proxy.PrintServiceProxy;

class PrintServiceTest {
    String pathString = "itemlist.txt cardlist.txt 3-1 2-5 5-1 card-1234";
    String[] parsedString = pathString.split(" ");
    StringParser stringParser = new StringParser(parsedString);
    FileItemStorage itemStorage = new FileItemStorage(parsedString[0]);
    PrintServiceInterface printService = new PrintService(stringParser.getParsedMap(), stringParser.getCard().getCardNumber(), itemStorage.initItemStorage());
    PrintServiceProxy printServiceProxy = new PrintServiceProxy(printService);
    @Test
    void printReceiptToConsole() {
        printServiceProxy.printReceiptToConsole();

    }

    @Test
    void printReceiptToFile() throws Exception{
        printServiceProxy.printReceiptToFile();
    }
}