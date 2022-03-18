package bookstore.controller.rest;

import bookstore.domain.Orders;
import bookstore.domain.Users;
import bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO:Add controller advice
@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/submitOrder")
    public ResponseEntity<Orders> submitOrder(final Users user) {
        orderService.submitOrder(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
