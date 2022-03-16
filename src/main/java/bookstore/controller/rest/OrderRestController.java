package bookstore.controller.rest;

import bookstore.dao.UserRepository;
import bookstore.domain.Orders;
import bookstore.domain.Users;
import bookstore.exception.BookstoreNotFoundException;
import bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static bookstore.utils.BookStoreConstants.USER_NOT_FOUND;

//TODO:Add controller advice
@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/submitOrder/{userId}")
    public ResponseEntity<Orders> submitOrder(final long userId) {
        final Optional<Users> foundUser = Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new BookstoreNotFoundException(USER_NOT_FOUND)));
        orderService.submitOrder(foundUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
