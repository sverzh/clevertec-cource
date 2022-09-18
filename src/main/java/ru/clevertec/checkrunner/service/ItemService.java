package ru.clevertec.checkrunner.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.checkrunner.exception.StorageException;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.repository.springdata.ItemStorageDataJpa;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemStorageDataJpa itemStorageDataJpa;


    public Page<Item> findAll(Pageable pageable) {
        return itemStorageDataJpa.findAll(pageable);
    }

    public Item update(Item source, int id) {
        Optional<Item> itemForUpdate = itemStorageDataJpa.findById(id);
        if(itemForUpdate.isPresent()) {
            Item target = itemForUpdate.get();
            target.setName(source.getName());
            target.setPrice(source.getPrice());
            target.setOffer(source.isOffer());
            this.itemStorageDataJpa.save(target);
            return target;
        } else {
            throw new StorageException("unable to update Item");
        }
    }

    public Item findById(int id) {
        return itemStorageDataJpa.findById(id).get();
    }

    public Item save(Item item) {
        return itemStorageDataJpa.save(item);
    }

    public void delete(int id) {
        this.itemStorageDataJpa.deleteById(id);
    }

}