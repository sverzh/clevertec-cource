package ru.clevertec.checkrunner.util;

import org.springframework.stereotype.Component;
import ru.clevertec.checkrunner.model.Card;
import ru.clevertec.checkrunner.model.Receipt;

import java.util.HashMap;
import java.util.Map;

public class StringParserWithoutFileStorages {
    private final String[] inputData;
    private Card card;
    private Receipt receipt = new Receipt();

    public StringParserWithoutFileStorages(String[] args) {
        this.inputData = args;
    }

    public Receipt getReceipt() {
        if (inputData != null) {
            for (String inputDatum : inputData) {
                String[] str1 = inputDatum.split("-");
                if (str1[0].equals("card")) {
                    card = new Card(Integer.parseInt(str1[1]));
                    break;
                }
                receipt.getItems().put(Integer.valueOf(str1[0]), Integer.valueOf(str1[1]));
//                parsedMap.put(Integer.valueOf(str1[0]), Integer.valueOf(str1[1]));
            }
        }
        return receipt;
    }

    public Card getCard() {
        return card;
    }
}


