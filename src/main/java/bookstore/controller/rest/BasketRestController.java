package bookstore.controller.rest;

import bookstore.domain.Basket;
import bookstore.domain.Book;
import bookstore.service.BasketService;
import bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

//TODO:Add controller advice
@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class BasketRestController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private BookService bookService;

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

    @PostMapping(value = "/addBookToBasket")
    public ResponseEntity<Basket> addBookToBasket(@RequestBody final Book book) {
        final Basket basket = bookService.addBookToBasket(book);
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    @PostMapping(value = "/removeBookFromBasket")
    public ResponseEntity<Basket> removeBookFromBasket(@RequestBody final Book book) { //change to id?
        final Basket basket = basketService.removeBookFromBasket(book);
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

}
