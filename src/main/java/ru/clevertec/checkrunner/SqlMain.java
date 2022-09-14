package ru.clevertec.checkrunner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import ru.clevertec.checkrunner.repository.CardSqlStorage;
import ru.clevertec.checkrunner.repository.ItemSqlStorage;
import ru.clevertec.checkrunner.service.PrintFromSqlService;
import ru.clevertec.checkrunner.service.PrintPdfService;
import ru.clevertec.checkrunner.service.PrintServiceInterface;
import ru.clevertec.checkrunner.service.proxy.PrintServiceProxy;
import ru.clevertec.checkrunner.util.StringParserWithoutFileStorages;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SqlMain {
    public static void main(String[] args) throws Exception {

        ItemSqlStorage itemSqlStorage;
        CardSqlStorage cardSqlStorage;

        String line = "3-2 4-5 2-5 card-1234";
        String[] args1 = line.split(" ");
        if (args1.length != 0) {
            StringParserWithoutFileStorages stringParser = new StringParserWithoutFileStorages(args1);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Where to print receipt? - 1-to Console | 2 - to File : \n");
            PrintServiceInterface printService = new PrintFromSqlService(stringParser.getReceipt().getItems(), stringParser.getCard());
//            PrintPdfService pdfService = new PrintPdfService(stringParser.getReceipt(), itemSqlStorage, cardSqlStorage, stringParser.getCard());
            PrintServiceInterface printServiceProxy = new PrintServiceProxy(printService);
            String command = reader.readLine();
            switch (command) {
                case "1":
//                    printServiceProxy.printReceiptToConsole();
                    break;
                case "2":
//                    printService.printReceiptToFile();
//                    pdfService.printReceiptToPdf();
//                    pdfService.printReceiptToPdf();
                    break;
                default:
                    System.out.println("Error: Wrong command");
                    break;
            }
        }
    }
}


