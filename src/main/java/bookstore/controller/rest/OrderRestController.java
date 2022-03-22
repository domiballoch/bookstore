package bookstore.controller.rest;

import bookstore.domain.OrderDetails;
import bookstore.domain.Users;
import bookstore.exception.BookstoreNotFoundException;
import bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookStoreConstants.ORDER_NOT_FOUND;

//TODO:Add controller advice
@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/findAllOrders")
    public ResponseEntity<List<OrderDetails>> findAllOrders() {
        final List<OrderDetails> orderDetails = orderService.findAllOrders();
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @GetMapping(value = "/findOrder/{orderDetailsId}")
    public ResponseEntity<Optional<OrderDetails>> findOrderById(@PathVariable final long orderDetailsId) {
        final Optional<OrderDetails> orderDetails = Optional.ofNullable(orderService.findOrderById(orderDetailsId))
                .orElseThrow(() -> new BookstoreNotFoundException(ORDER_NOT_FOUND));
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @PostMapping(value = "/submitOrder")
    public ResponseEntity<OrderDetails> submitOrder(@RequestBody final Users user) {
        final OrderDetails orderDetails = orderService.submitOrder(user);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }
}
