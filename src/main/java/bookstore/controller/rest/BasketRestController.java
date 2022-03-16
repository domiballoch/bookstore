package bookstore.controller.rest;

import bookstore.domain.Basket;
import bookstore.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

//TODO:Add controller advice
@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class BasketRestController {

    @Autowired
    private BasketService basketService;

    @GetMapping(value = "/getBasket")
    public ResponseEntity<Basket> getBasket() {
        final Basket getBasket = basketService.getBasket();
        return new ResponseEntity<>(getBasket, HttpStatus.OK);
    }

    @GetMapping(value = "/calcBasket")
    public ResponseEntity<BigDecimal> calcBasket(final Basket basket) {
        final BigDecimal calculatedBasket = basketService.calculateBasket(basket);
        return new ResponseEntity<>(calculatedBasket, HttpStatus.OK);
    }

}
