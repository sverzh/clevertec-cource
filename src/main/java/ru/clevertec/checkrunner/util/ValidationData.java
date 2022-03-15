package ru.clevertec.checkrunner.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationData {
    private String[] parsedLine;
    private final String idPattern = "^[1-9][0-9]?$|^100$";
    private final String namePattern = "^[A-Z][a-z]{2,29}";
    private final String pricePattern = "^([1-9]{1,2}[.](\\d{2})|100.00)";
    private final String countPattern = "^(([1-9]|1[0-9])|20)$";

    public ValidationData(String parseLine) {
        parsedLine = parseLine.split(";");
    }

    public boolean isCorrectData() {
        return isValidData(parsedLine[0], idPattern) &&
                isValidData(parsedLine[1], namePattern) &&
                isValidData(parsedLine[2], pricePattern) &&
                isValidData(parsedLine[3], countPattern);
    }

    private boolean isValidData(String data, String regex) {
        Pattern idPattern = Pattern.compile(regex);
        Matcher matcher = idPattern.matcher(data);
        return matcher.find();
    }
}
