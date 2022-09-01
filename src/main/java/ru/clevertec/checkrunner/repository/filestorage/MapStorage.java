package ru.clevertec.checkrunner.repository.filestorage;

import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage  {
    private final static Map<Integer, Item> map = new HashMap<>();


    public Item get(int id) {
        return map.get(id);
    }


    public void add(Item item) {
        map.put(item.getId(), item);
    }


    public void update(Item item) {
    }


    public void delete(int id) {

    }


    public List<Item> findAll() {
        return null;
    }
}