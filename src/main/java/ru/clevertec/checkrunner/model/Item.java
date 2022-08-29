package ru.clevertec.checkrunner.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class Item {

    private final int id;
    private String name;
    private double price;
    private boolean offer;
}
