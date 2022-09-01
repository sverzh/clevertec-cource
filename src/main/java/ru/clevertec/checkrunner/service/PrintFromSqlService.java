package ru.clevertec.checkrunner.service;

import ru.clevertec.checkrunner.annotation.Log;
import ru.clevertec.checkrunner.annotation.LoggingLevel;
import ru.clevertec.checkrunner.model.Card;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.CardNumberSqlStorage;
import ru.clevertec.checkrunner.repository.ItemSqlStorage;

import java.io.File;
import java.io.PrintStream;
import java.util.Map;

public class PrintFromSqlService implements PrintServiceInterface {
    private final Map<Integer, Integer> paramsMap;
    private final String cardNumber;
    private final int cardDiscount;
    private final ItemSqlStorage itemSqlStorage = new ItemSqlStorage();
    private final CardNumberSqlStorage cardSqlStorage = new CardNumberSqlStorage();
    private double total = 0;
    private double discountTotal = 0;

    public PrintFromSqlService(Map<Integer, Integer> paramsMap, Card card) {
        this.paramsMap = paramsMap;
        this.cardNumber = card.getCardNumber();
        this.cardDiscount= card.getDiscount();
    }

    @Override
    @Log(LoggingLevel.INFO)
    public void printReceiptToConsole() {
        checkAllIdInReceipt();
        if (!paramsMap.isEmpty()) {
            System.out.println("-------------------------CASH RECEIPT----------------------------");
            System.out.println("QTY        DESCRIPTION                  PRICE         TOTAL");
            calculation();
            System.out.println("-----------------------------------------------------------------");
            if (cardSqlStorage.get(cardNumber)!=null) {
                System.out.println("Discount Card : " + cardNumber);
            }
            if (discountTotal != 0) {
                System.out.printf("DISCOUNT:                                             %.2f\n", discountTotal);
            }
            System.out.println("TOTAL:                                                " + total);
            if (cardSqlStorage.get(cardNumber)==null && cardNumber != null) {
                System.out.println("Notice: Card with number " + cardNumber + " not found!");
            }
        }
    }


    @Override
    public void printReceiptToFile() {
        checkAllIdInReceipt();
        String pathToFile = new File(System.getProperty("user.dir")).getPath() + "\\receipt.txt";
        try (PrintStream fw = new PrintStream(pathToFile)) {
            if (!paramsMap.isEmpty()) {
                fw.println("-------------------------CASH RECEIPT----------------------------");
                fw.println("QTY        DESCRIPTION                  PRICE         TOTAL");
                calculation();
                fw.println("-----------------------------------------------------------------");
                if (cardSqlStorage.get(cardNumber)!=null) {
                    fw.println("Discount Card : " + cardNumber);
                }
                if (discountTotal != 0) {
                    fw.printf("DISCOUNT:                                              %.2f\n", discountTotal);
                }
                fw.println("TOTAL:                                                " + total);
                if (cardSqlStorage.get(cardNumber)==null && cardNumber != null) {
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
            Item item = itemSqlStorage.get(entry.getKey());
            if (item == null) {
                throw new RuntimeException("Item not found");
            }
        }

    }
    private void calculation(){
        for (Map.Entry<Integer, Integer> entry : paramsMap.entrySet()) {
            Integer qty = entry.getValue();
            double rebate = 1;
            Item item = itemSqlStorage.get(entry.getKey());
            String description = item.getName();
            if (cardSqlStorage.get(cardNumber)!=null && qty >= 5 && item.isOffer()) {
                rebate = 1-cardDiscount*rebate/100;
                description = description + "(discount "+cardDiscount+"%)";
            }
            double totalOfItems = item.getPrice() * qty * rebate;
            System.out.printf("%-4s       %-20s         %.2f          %.2f\n"
                    , qty
                    , description
                    , item.getPrice(), totalOfItems);

            discountTotal = discountTotal + item.getPrice() * qty - totalOfItems;
            total = total + totalOfItems;
        }
    }
}
