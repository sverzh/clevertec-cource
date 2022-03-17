package ru.clevertec.checkrunner;

import ru.clevertec.checkrunner.util.ValidationData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FeatureRegexMain {
    public static void main(String[] args) {
        File fileInput = new File("src/main/resources/not_filtered_itemslist.txt");
        File fileOutput = new File("src/main/resources/invalidData.txt");

        try (FileReader fr = new FileReader(fileInput);
             FileWriter fw = new FileWriter(fileOutput)) {
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                ValidationData validationLine = new ValidationData(line);
                if (!validationLine.isCorrectData()) {
                    fw.write(line);
                    fw.write("\n");
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Something wrong with init Card Storage!!! \n" + e.getMessage());
        }

    }
}
