package ru.clevertec.checkrunner.service.proxy;

import ru.clevertec.checkrunner.service.PrintServiceInterface;
import ru.clevertec.checkrunner.service.hadler.PrintServiceHandler;

import java.lang.reflect.Proxy;

public class PrintServiceProxy implements PrintServiceInterface {
    private static PrintServiceInterface printService;


    public PrintServiceProxy(PrintServiceInterface printService) {
        ClassLoader productServiceClassLoader = printService.getClass().getClassLoader();
        Class<?>[] productServiceInterfaces = printService.getClass().getInterfaces();
        PrintServiceProxy.printService = (PrintServiceInterface) Proxy.newProxyInstance(productServiceClassLoader,
                productServiceInterfaces, new PrintServiceHandler(printService));
    }

    @Override
    public void printReceiptToConsole() {
        printService.printReceiptToConsole();
        System.out.println("Proxy Working");
    }

    @Override
    public void printReceiptToFile() {
        printService.printReceiptToFile();
        System.out.println("Proxy Working");
    }
}
