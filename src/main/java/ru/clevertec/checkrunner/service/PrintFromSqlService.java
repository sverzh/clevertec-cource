package ru.clevertec.checkrunner.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import ru.clevertec.checkrunner.annotation.Log;
import ru.clevertec.checkrunner.annotation.LoggingLevel;
import ru.clevertec.checkrunner.model.Card;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.CardSqlStorage;
import ru.clevertec.checkrunner.repository.ItemSqlStorage;

import java.io.*;
import java.util.Map;

public class PrintFromSqlService implements PrintServiceInterface {
    private final Map<Integer, Integer> paramsMap;
    private final int cardNumber;
    private final int cardDiscount;
    private final ItemSqlStorage itemSqlStorage = new ItemSqlStorage();
    private final CardSqlStorage cardSqlStorage = new CardSqlStorage();
    private double total = 0;
    private double discountTotal = 0;
    PrintStream fw;
    String pathToFile = new File(System.getProperty("user.dir")).getPath() + "\\receipt.txt";
    String pathToPdfFile = new File(System.getProperty("user.dir")).getPath() + "\\receipt1.pdf";

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
            if (cardSqlStorage.get(cardNumber)==null) {
                System.out.println("Discount Card : " + cardNumber);
            }
            if (discountTotal != 0) {
                System.out.printf("DISCOUNT:                                             %.2f\n", discountTotal);
            }
            System.out.println("TOTAL:                                                " + total);
            if (cardSqlStorage.get(cardNumber)==null && cardNumber != 0) {
                System.out.println("Notice: Card with number " + cardNumber + " not found!");
            }
        }
    }


    @Override
    public void printReceiptToFile() throws Exception{
        checkAllIdInReceipt();

        try {
            fw = new PrintStream(pathToFile);
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
                if (cardSqlStorage.get(cardNumber)==null && cardNumber !=0) {
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
            fw.printf("%-4s       %-20s         %.2f          %.2f\n"
                    , qty
                    , description
                    , item.getPrice(), totalOfItems);

            discountTotal = discountTotal + item.getPrice() * qty - totalOfItems;
            total = total + totalOfItems;
        }
    }
}
