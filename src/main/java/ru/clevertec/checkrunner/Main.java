package ru.clevertec.checkrunner;

import ru.clevertec.checkrunner.repository.FileCardStorage;
import ru.clevertec.checkrunner.repository.FileItemStorage;
import ru.clevertec.checkrunner.service.PrintService;
import ru.clevertec.checkrunner.service.PrintServiceInterface;
import ru.clevertec.checkrunner.service.proxy.PrintServiceProxy;
import ru.clevertec.checkrunner.util.StringParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {



        if (args.length != 0) {
            StringParser stringParser = new StringParser(args);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            FileItemStorage itemStorage = new FileItemStorage(args[0]);
            FileCardStorage cardStorage = new FileCardStorage(args[1]);
            if (cardStorage.initCardList()) {
                System.out.print("Where to print receipt? - 1-to Console | 2 - to File : \n");
                PrintServiceInterface printService = new PrintService(stringParser.getParsedMap(), stringParser.getCardNumber(), itemStorage.initItemStorage());
                PrintServiceInterface printServiceProxy = new PrintServiceProxy(printService);
                String command = reader.readLine();
                switch (command) {
                    case "1":
                        printServiceProxy.printReceiptToConsole();
                        break;
                    case "2":
                        printServiceProxy.printReceiptToFile();
                        break;
                    default:
                        System.out.println("Error: Wrong command");
                        break;
                }
            }
        } else {
            System.out.println("Error: No Parameters");
        }
    }

}

