package ru.clevertec.checkrunner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.checkrunner.model.Card;
import ru.clevertec.checkrunner.service.CardService;

@RestController
@RequestMapping({ "/api/" })
@RequiredArgsConstructor
public class CardController
{
    private final CardService cardService;

    @GetMapping({ "/cards" })
    public ResponseEntity<Page<Card>> getAllCards(@PageableDefault(page = 0, size = 20) final Pageable pageable) {
        final Page<Card> result = cardService.findAll(pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping({ "/card" })
    public ResponseEntity<Card> save(@RequestBody final Card card) {
        final Card result = this.cardService.save(card);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping({ "/card/{number}" })
    public ResponseEntity<Card> getCardByNumber(@PathVariable final int number) {
        final Card result = this.cardService.findByNumber(number);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping({ "/item/{number}" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final int number) {
        this.cardService.delete(number);
    }

}
