package bookstore.controller;

import bookstore.domain.Basket;
import bookstore.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasketRestController {

    @Autowired
    private BasketService basketService;

    @GetMapping(value = "/getBasket/rest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Basket> getBasket(final Basket basket) {
        basketService.calculateBasket(basket);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
