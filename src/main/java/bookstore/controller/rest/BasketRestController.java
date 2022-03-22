package bookstore.controller.rest;

import bookstore.domain.Book;
import bookstore.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//TODO:Add controller advice
@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class BasketRestController {

    @Autowired
    private BasketService basketService;

    @GetMapping(value = "/getBasket")
    public ResponseEntity<List<Book>> getBasket() {
        final List<Book> getBasket = basketService.getBasket();
        return new ResponseEntity<>(getBasket, HttpStatus.OK);
    }

    @DeleteMapping(value = "/removeBookFromBasket/{isbn}")
    public ResponseEntity<List<Book>> removeBookFromBasket(@PathVariable final long isbn) {
        final List<Book> basket = basketService.removeBookFromBasket(isbn);
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

}
