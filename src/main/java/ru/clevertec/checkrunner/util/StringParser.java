package ru.clevertec.checkrunner.util;

import ru.clevertec.checkrunner.model.Card;

import java.util.HashMap;
import java.util.Map;

public class StringParser {
    private final String[] inputData;
    private final Map<Integer, Integer> parsedMap = new HashMap<>();
    private Card card;

    public StringParser(String[] args) {
        this.inputData = args;
    }

    public Map<Integer, Integer> getParsedMap() {
        if (inputData != null) {
            for (int i = 2; i < inputData.length; i++) {
                String[] str1 = inputData[i].split("-");
                if (str1[0].equals("card")) {
                    card = new Card(Integer.valueOf(str1[1]));
                    break;
                }
                parsedMap.put(Integer.valueOf(str1[0]), Integer.valueOf(str1[1]));
            }
        }
        return parsedMap;
    }

    public Card getCard() {
        return card;
    }

}
