package ru.clevertec.checkrunner.repository;

import ru.clevertec.checkrunner.model.Item;

import java.util.HashMap;
import java.util.Map;

public class MapStorage implements Storage {
    private final static Map<Integer, Item> map = new HashMap<>();

    @Override
    public Item get(int id) {
        return map.get(id);
    }

    @Override
    public void save(Item item) {
        map.put(item.getId(), item);
    }

}