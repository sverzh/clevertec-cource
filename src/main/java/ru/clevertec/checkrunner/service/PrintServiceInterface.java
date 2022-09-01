package ru.clevertec.checkrunner.service;

public interface PrintServiceInterface {

    void printReceiptToConsole();

    void printReceiptToFile() throws Exception;
}
