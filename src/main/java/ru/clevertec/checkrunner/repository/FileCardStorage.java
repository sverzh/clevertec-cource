package ru.clevertec.checkrunner.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileCardStorage {
    final static List<String> cardList = new ArrayList<>();
    private final String fileName;

    public FileCardStorage(String fileName) {
        this.fileName = fileName;
    }

    public boolean initCardList() {
        String pathFile = new File(System.getProperty("user.dir")).getPath() + "\\" + fileName;
        File file = new File(pathFile);
        try (FileReader fr = new FileReader(file)) {
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                cardList.add(line);
                line = reader.readLine();
            }
            return true;
        } catch (Exception e) {
            System.out.println("Something wrong with init Card Storage!!! \n" + e.getMessage());
        }
        return false;
    }

    public static boolean checkCard(String cardNumber) {
        return cardList.contains(cardNumber);
    }

}
