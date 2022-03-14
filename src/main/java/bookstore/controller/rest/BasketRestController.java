package bookstore.controller.rest;

import bookstore.dao.UserRepository;
import bookstore.domain.Basket;
import bookstore.domain.Orders;
import bookstore.domain.Users;
import bookstore.exception.BookstoreNotFoundException;
import bookstore.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

import static bookstore.utils.BookConstants.USER_NOT_FOUND;

//TODO:Add controller advice
@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class BasketRestController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private UserRepository userRepository;

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

    @PostMapping(value = "/submitOrder/{userId}")
    public ResponseEntity<Orders> submitOrder(final long userId) {
        final Optional<Users> foundUser = Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new BookstoreNotFoundException(USER_NOT_FOUND)));
        basketService.submitOrder(foundUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
