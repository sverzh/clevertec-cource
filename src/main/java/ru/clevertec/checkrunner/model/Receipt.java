package ru.clevertec.checkrunner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class Receipt {
    private  Map<Integer,Integer> items = new HashMap<>();
    private  Card card;
}
