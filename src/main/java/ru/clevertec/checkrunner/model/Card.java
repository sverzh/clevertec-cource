package ru.clevertec.checkrunner.model;

import lombok.Data;

@Data
public class Card {

    private final int cardNumber;
    private int discount=5;

}
