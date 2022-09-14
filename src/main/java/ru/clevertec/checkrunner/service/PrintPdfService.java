package ru.clevertec.checkrunner.service;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.annotation.Log;
import ru.clevertec.checkrunner.annotation.LoggingLevel;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.model.Receipt;
import ru.clevertec.checkrunner.repository.CardSqlStorage;
import ru.clevertec.checkrunner.repository.ItemSqlStorage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class PrintPdfService {
    private Receipt receipt;
    private final ItemSqlStorage itemSqlStorage;
    private final CardSqlStorage cardSqlStorage;
    private final String pathToFile = new File("D:\\project\\src\\main\\resources\\receipt.pdf").getPath();

    public PrintPdfService(ItemSqlStorage itemSqlStorage, CardSqlStorage cardSqlStorage) {
        this.itemSqlStorage = itemSqlStorage;
        this.cardSqlStorage = cardSqlStorage;
    }


    @Log(LoggingLevel.INFO)
    public void printReceiptToPdf(Receipt receipt) throws IOException {
        this.receipt = receipt;
        int cardNumber = receipt.getCard().getNumber();
        int cardDiscount = receipt.getCard().getDiscount();
        double total = 0;
        double discountTotal = 0;
        checkAllIdInReceipt();
        Document document = new Document(PageSize.A5);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(pathToFile));
            document.open();
            if (!receipt.getItems().isEmpty()) {
                float[] widths1 = {200F};
                PdfPTable table1 = new PdfPTable(widths1);
                PdfPCell cell;
                table1.addCell(setTextCenter("CASH RECEIPT"));
                table1.addCell(setTextCenter("SUPERMARKET"));
                LocalDate date = LocalDate.now();
                DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalTime time = LocalTime.now();
                DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
                table1.addCell(setTextLeft("TERMINAL 1"));
                table1.addCell(setTextRight(date.format(formatterDate)));
                table1.addCell(setTextRight(time.format(formatterTime)));
                table1.addCell(setTextCenter("-------------------------------------------------------------------"));
                float[] widths2 = {50f, 150f, 50f, 50f};
                PdfPTable table2 = new PdfPTable(widths2);
                table2.addCell(setTextLeft("QTY"));
                table2.addCell(setTextLeft("DESCRIPTION"));
                table2.addCell(setTextLeft("PRICE"));
                table2.addCell(setTextLeft("TOTAL"));

                float[] widths4 = {50f, 150f, 50f, 50f};
                PdfPTable table4 = new PdfPTable(widths4);
                for (Map.Entry<Integer, Integer> entry : receipt.getItems().entrySet()) {
                    Integer qty = entry.getValue();
                    double rebate = 1;
                    Item item = itemSqlStorage.get(entry.getKey());
                    String description = item.getName();
                    if (cardSqlStorage.get(cardNumber) != null && qty >= 5 && item.isOffer()) {
                        rebate = 1 - cardDiscount * rebate / 100;
                        description = description + "(discount " + cardDiscount + "%)";
                    }
                    double totalOfItems = item.getPrice() * qty * rebate;

                    table4.addCell(setTextLeft(String.valueOf(qty)));
                    table4.addCell(setTextLeft(description));
                    table4.addCell(setTextLeft(String.format("%.2f", item.getPrice())));
                    table4.addCell(setTextLeft(String.format("%.2f", totalOfItems)));

                    discountTotal = discountTotal + item.getPrice() * qty - totalOfItems;
                    total = total + totalOfItems;
                }

                cell = new PdfPCell(new Paragraph("======================================="));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setColspan(4);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table2.addCell(cell);

                float[] widths3 = {100f, 100f};
                PdfPTable table3 = new PdfPTable(widths3);

                if (cardSqlStorage.get(cardNumber) != null) {
                    table3.addCell(setTextLeft("Discount Card : "));
                    table3.addCell(setTextLeft(String.valueOf(cardNumber)));

                }
                if (discountTotal != 0) {
                    table3.addCell(setTextLeft("DISCOUNT: "));
                    table3.addCell(setTextCenter(String.format("                            %.2f", discountTotal)));
                }
                table3.addCell(setTextLeft("TOTAL: "));
                table3.addCell(setTextCenter(String.format("                        %.2f", total)));

                if (cardSqlStorage.get(cardNumber) == null && cardNumber != 0) {
                    cell = new PdfPCell(new Paragraph("Notice: Card with number " + cardNumber + " not found!"));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setColspan(2);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table3.addCell(cell);
                }

                document.add(table1);
                document.add(table2);
                document.add(table4);
                document.add(table3);
            }
            document.close();
            System.out.println("Receipt Saved to:            " + pathToFile);
        } catch (Exception e) {
            System.out.println("Something wrong with output to file: " + e.getMessage());
        }
    }


    private void checkAllIdInReceipt() {
        for (Map.Entry<Integer, Integer> entry : receipt.getItems().entrySet()) {
            Item item = itemSqlStorage.get(entry.getKey());
            if (item == null) {
                throw new RuntimeException("Item not found");
            }
        }
    }

    private PdfPCell setTextLeft(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return cell;
    }

    private PdfPCell setTextRight(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return cell;
    }

    private PdfPCell setTextCenter(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

}
