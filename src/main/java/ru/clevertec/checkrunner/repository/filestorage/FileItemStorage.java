package ru.clevertec.checkrunner.repository.filestorage;

import ru.clevertec.checkrunner.model.Item;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileItemStorage {
    public static MapStorage itemStorage = new MapStorage();
    private static int id = 0;
    private final String fileName;

    public FileItemStorage(String fileName) {
        this.fileName = fileName;
    }

    public MapStorage initItemStorage() {
//        String pathFile = new File(System.getProperty("user.dir")).getPath() + "\\" + fileName;
        String pathFile = "src/main/resources/"+fileName;
        File file = new File(pathFile);
        try (
                FileReader fr = new FileReader(file)) {
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                String[] parseLine = line.split("_");
                id++;
                itemStorage.save(new Item(id, parseLine[0], Double.parseDouble(parseLine[1]), parseLine[2].equals("true")));
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Something wrong with init Item Storage!!!\n" + e.getMessage());
        }
        return itemStorage;
    }
}
