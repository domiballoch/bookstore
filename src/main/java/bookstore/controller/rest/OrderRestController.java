package bookstore.controller.rest;

import bookstore.dao.UserRepository;
import bookstore.domain.OrderDetails;
import bookstore.domain.Orders;
import bookstore.service.BookService;
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

    @Autowired
    private BookService bookService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/submitOrder")
    public ResponseEntity<Orders> submitOrder(final OrderDetails orderDetails) {
//        final Optional<Users> foundUser = Optional.ofNullable(userRepository.findById(userId)
//                .orElseThrow(() -> new BookstoreNotFoundException(USER_NOT_FOUND)));
        orderService.submitOrder(orderDetails);
        bookService.updateBookStock(orderDetails);//TODO:ForEach book in orderDetails.getBooks
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
