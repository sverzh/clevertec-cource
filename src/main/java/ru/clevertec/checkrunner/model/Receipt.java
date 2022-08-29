package ru.clevertec.checkrunner.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class Receipt {
    private final Map<Integer,Integer> items = new HashMap<>();
}
