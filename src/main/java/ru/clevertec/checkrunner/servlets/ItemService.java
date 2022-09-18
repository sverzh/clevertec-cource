package ru.clevertec.checkrunner.servlets;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.springdata.ItemStorageDataJpa;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemStorageDataJpa itemStorageDataJpa;

    public Page<Item> findAll(String numberPage, String pageSize) {
        int size = pageSize != null ? Integer.parseInt(pageSize) : 20;
        int page = numberPage != null ? (Integer.parseInt(numberPage) * size) : 0;
        return itemStorageDataJpa.findAll(PageRequest.of(page, size));
    }

    public Item update(Item item) {
        Optional<Item> optionalItem = itemStorageDataJpa.findByName(item.getName());
        if (optionalItem.isPresent()) {
            Item target = optionalItem.get();
            target.setPrice(item.getPrice());
            target.setOffer(item.isOffer());
            itemStorageDataJpa.save(target);
        }
        return item;
    }

    public Item findById(int id) {
        return itemStorageDataJpa.findById(id).get();
    }

    public Item save(Item item) {
        return itemStorageDataJpa.save(item);
    }

    public void delete(int id) {
        itemStorageDataJpa.deleteById(id);
    }

}
