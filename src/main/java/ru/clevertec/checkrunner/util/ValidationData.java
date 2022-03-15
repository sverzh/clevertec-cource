package ru.clevertec.checkrunner.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationData {
    private String[] parsedLine;

    public ValidationData(String parseLine) {
        parsedLine = parseLine.split(";");
    }

    public boolean isCorrectData() {
        return checkId() && checkName() && checkPrice() && checkCount();
    }

    public String[] parsedLine() {
        if (isCorrectData()) {
            return parsedLine;
        }
        return null;
    }

    private boolean checkId() {
        Pattern idPattern = Pattern.compile("^[0-9][0-9]?$|^100$");
        Matcher matcher = idPattern.matcher(parsedLine[0]);
        return matcher.find();
    }

    private boolean checkName() {
        Pattern idPattern = Pattern.compile("^[A-Z].[a-z]{1,30}\\b");
        Matcher matcher = idPattern.matcher(parsedLine[1]);
        return matcher.find();
    }

    private boolean checkPrice() {
        Pattern idPattern = Pattern.compile("^(\\d{1,2}|100)[.]\\d{2}\\b");
        Matcher matcher = idPattern.matcher(parsedLine[2]);
        return matcher.find();
    }

    private boolean checkCount() {
        Pattern idPattern = Pattern.compile("^(([1-9]|1[0-9])|20)$");
        Matcher matcher = idPattern.matcher(parsedLine[3]);
        return matcher.find();
    }
}
