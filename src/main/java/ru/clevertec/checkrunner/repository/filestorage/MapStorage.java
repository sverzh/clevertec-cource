package ru.clevertec.checkrunner.repository.filestorage;

import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage implements Storage<Item> {
    private final static Map<Integer, Item> map = new HashMap<>();

    @Override
    public Item get(int id) {
        return map.get(id);
    }

    @Override
    public void save(Item item) {
        map.put(item.getId(), item);
    }

    @Override
    public void update(Item item) {
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Item> findAll() {
        return null;
    }
}