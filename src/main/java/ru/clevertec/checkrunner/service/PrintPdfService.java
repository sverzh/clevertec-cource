package ru.clevertec.checkrunner.service;



import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.pdf.PdfWriter;
import ru.clevertec.checkrunner.annotation.Log;
import ru.clevertec.checkrunner.annotation.LoggingLevel;
import ru.clevertec.checkrunner.model.Card;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.CardSqlStorage;
import ru.clevertec.checkrunner.repository.ItemSqlStorage;


import java.io.File;
import java.io.FileOutputStream;

import java.util.Map;

public class PrintPdfService {
    private final Map<Integer, Integer> paramsMap;
    private final ItemSqlStorage itemSqlStorage = new ItemSqlStorage();
    private final CardSqlStorage cardSqlStorage = new CardSqlStorage();
    private final int cardNumber;
    private final int cardDiscount;
    private double total = 0;
    private double discountTotal = 0;
    Document document = new Document(PageSize.A4);

    public PrintPdfService(Map<Integer, Integer> paramsMap, Card card) {
        this.paramsMap = paramsMap;
        this.cardNumber = card.getCardNumber();
        this.cardDiscount= card.getDiscount();
    }

    @Log(LoggingLevel.INFO)
    public void printReceiptToPdf() {
        checkAllIdInReceipt();
        String pathToFile = new File(System.getProperty("user.dir")).getPath() + "\\receipt.pdf";
        try  {
            PdfWriter.getInstance(document, new FileOutputStream(pathToFile));
            document.open();
            if (!paramsMap.isEmpty()) {
                document.add(new Paragraph("-------------------------CASH RECEIPT---------------------------"));
                document.add(new Paragraph("QTY      DESCRIPTION                  PRICE         TOTAL"));
                calculation();
                document.add(new Paragraph("------------------------------------------------------------------------"));
                if (cardSqlStorage.get(cardNumber)!=null) {
                    document.add(new Paragraph("Discount Card : " + cardNumber));
                }
                if (discountTotal != 0) {
                    document.add(new Paragraph(String.format("DISCOUNT:                                               %.2f", discountTotal)));

                }
                document.add(new Paragraph("TOTAL:                                                              " + total));
                if (cardSqlStorage.get(cardNumber)==null && cardNumber != 0) {
                    document.add(new Paragraph("Notice: Card with number " + cardNumber + " not found!"));
                }
            }
            document.close();
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

    private void calculation() throws Exception{
        for (Map.Entry<Integer, Integer> entry : paramsMap.entrySet()) {
            Integer qty = entry.getValue();
            double rebate = 1;
            Item item = itemSqlStorage.get(entry.getKey());
            String description = item.getName();
            if (cardSqlStorage.get(cardNumber)!=null && qty >= 5 && item.isOffer()) {
                rebate = 1-cardDiscount*rebate/100;
                description = description + " (discount "+cardDiscount+"%)";
            }
            double totalOfItems = item.getPrice() * qty * rebate;
            document.add(new Paragraph(String.format("%-4s         %-25s       %7.2f         %7.2f"
                    , qty
                    , description
                    , item.getPrice(), totalOfItems)));
            discountTotal = discountTotal + item.getPrice() * qty - totalOfItems;
            total = total + totalOfItems;
        }
    }


}
