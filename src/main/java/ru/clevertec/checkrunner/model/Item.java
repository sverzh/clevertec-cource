package ru.clevertec.checkrunner.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private  int id;
    private String name;
    private double price;
    private boolean offer;


}
