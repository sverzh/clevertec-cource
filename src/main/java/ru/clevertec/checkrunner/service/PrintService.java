package ru.clevertec.checkrunner.service;

import ru.clevertec.checkrunner.annotation.Log;
import ru.clevertec.checkrunner.annotation.LoggingLevel;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.filestorage.FileCardStorage;
import ru.clevertec.checkrunner.repository.filestorage.MapStorage;

import java.io.File;

import java.io.PrintStream;
import java.util.Map;

public class PrintService implements PrintServiceInterface {

    private final Map<Integer, Integer> paramsMap;
    private final MapStorage itemStorage;
    private final int cardNumber;

    public PrintService(Map<Integer, Integer> paramsMap, int cardNumber, MapStorage itemStorage) {
        this.paramsMap = paramsMap;
        this.itemStorage = itemStorage;
        this.cardNumber = cardNumber;
    }

    @Log(LoggingLevel.INFO)
    public void printReceiptToConsole() {
        checkAllIdInReceipt();
        double total = 0;
        double discountTotal = 0;
        if (!paramsMap.isEmpty()) {
            System.out.println("-------------------------CASH RECEIPT----------------------------");
            System.out.println("QTY        DESCRIPTION                  PRICE         TOTAL");
            for (Map.Entry<Integer, Integer> entry : paramsMap.entrySet()) {
                Integer qty = entry.getValue();
                double rebate = 1;
                Item item = itemStorage.get(entry.getKey());
                String description = item.getName();
                if (FileCardStorage.checkCard(cardNumber) && qty >= 5 && item.isOffer()) {
                    rebate = 0.9;
                    description = description + "(discount 10%)";
                }
                double totalOfItems = item.getPrice() * qty * rebate;
                System.out.printf("%-4s       %-20s         %.2f          %.2f\n"
                        , qty
                        , description
                        , item.getPrice(), totalOfItems);

                discountTotal = discountTotal + item.getPrice() * qty - totalOfItems;
                total = total + totalOfItems;
            }
            System.out.println("-----------------------------------------------------------------");
            if (FileCardStorage.checkCard(cardNumber)) {
                System.out.println("Discount Card : " + cardNumber);
            }
            if (discountTotal != 0) {
                System.out.println("DISCOUNT:                                             -" + discountTotal);
            }
            System.out.println("TOTAL:                                                " + total);
            if (!FileCardStorage.checkCard(cardNumber) && cardNumber != 0) {
                System.out.println("Notice: Card with number " + cardNumber + " not found!");
            }
        }
    }

    @Log(LoggingLevel.INFO)
    public void printReceiptToFile() {
        checkAllIdInReceipt();
        String pathToFile = new File(System.getProperty("user.dir")).getPath() + "\\receipt.txt";
        try (PrintStream fw = new PrintStream(pathToFile)) {
            double total = 0;
            double discountTotal = 0;
            if (!paramsMap.isEmpty()) {
                fw.println("-------------------------CASH RECEIPT----------------------------");
                fw.println("QTY        DESCRIPTION                  PRICE         TOTAL");
                for (Map.Entry<Integer, Integer> entry : paramsMap.entrySet()) {
                    Integer qty = entry.getValue();
                    double rebate = 1;
                    Item item = itemStorage.get(entry.getKey());
                    String description = item.getName();
                    if (FileCardStorage.checkCard(cardNumber) && qty >= 5 && item.isOffer()) {
                        rebate = 0.9;
                        description = description + "(discount 10%)";
                    }
                    double totalOfItems = item.getPrice() * qty * rebate;
                    fw.printf("%-4s       %-20s         %.2f          %.2f\n"
                            , qty
                            , description
                            , item.getPrice(), totalOfItems);

                    discountTotal = discountTotal + item.getPrice() * qty - totalOfItems;
                    total = total + totalOfItems;
                }
                fw.println("-----------------------------------------------------------------");
                if (FileCardStorage.checkCard(cardNumber)) {
                    fw.println("Discount Card : " + cardNumber);
                }
                if (discountTotal != 0) {
                    fw.println("DISCOUNT:                                             -" + discountTotal);
                }
                fw.println("TOTAL:                                                " + total);
                if (!FileCardStorage.checkCard(cardNumber) && cardNumber != 0) {
                    System.out.println("Notice: Card with number " + cardNumber + " not found!");
                }
            }
            System.out.println("Receipt Saved to:            " + pathToFile);
        } catch (Exception e) {
            System.out.println("Something wrong with output to file: " + e.getMessage());
        }
    }

    private void checkAllIdInReceipt() {
        for (Map.Entry<Integer, Integer> entry : paramsMap.entrySet()) {
            Item item = itemStorage.get(entry.getKey());
            if (item == null) {
                throw new RuntimeException("Item not found");
            }
        }
    }
}