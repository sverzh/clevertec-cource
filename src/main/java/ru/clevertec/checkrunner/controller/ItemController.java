package ru.clevertec.checkrunner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.checkrunner.model.Item;
import ru.clevertec.checkrunner.service.ItemService;

@RestController
@RequestMapping({"/api/"})
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping({"/items"})
    public ResponseEntity<Page<Item>> getAllItems(@PageableDefault(page = 0, size = 20) final Pageable pageable) {
        final Page<Item> result = itemService.findAll(pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping({"/item"})
    public ResponseEntity<Item> save(@RequestBody final Item item) {
        final Item result = this.itemService.save(item);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping({"/item/{id}"})
    public ResponseEntity<Item> getItem(@PathVariable final int id) {
        final Item result = this.itemService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping({"/item/{id}"})
    public ResponseEntity<Item> updateItem(@PathVariable final int id, @RequestBody final Item source) {
        final Item result = this.itemService.update(source, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping({"/item/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final int id) {
        this.itemService.delete(id);
    }

}