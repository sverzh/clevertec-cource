package ru.clevertec.checkrunner.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Card {

    private final int cardNumber;
    private int discount=5;

}
