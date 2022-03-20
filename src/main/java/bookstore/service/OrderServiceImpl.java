package bookstore.service;

import bookstore.dao.OrderRepository;
import bookstore.dao.UserRepository;
import bookstore.domain.Basket;
import bookstore.domain.OrderDetails;
import bookstore.domain.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private Basket basket;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BasketService basketService;

    @Autowired
    private BookService bookService;

    /**
     * Saves orderDetails to DB
     * User checked if exist, if false then save details
     * Basket is added to orderDetails and attached to user with date
     *
     * @param user
     */
    @Override
    public OrderDetails submitOrder(final Users user) {
        final Optional<Users> foundUser = userRepository.findById(user.getUserId());
        foundUser.ifPresent(u -> {
            userRepository.save(u);
            log.info("User not found so saving details {}", user.toString());
        });

        log.info("Saving order details: {}");
        OrderDetails newOrderDetails = OrderDetails.builder()
                .bookList(basket.getBooks())
                .totalPrice(basketService.calculateBasket(basket))
                .users(user)
                .orderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)).build();

        orderRepository.save(newOrderDetails);
        log.info("Order complete: {}", newOrderDetails.toString());

        bookService.updateBookStock(basket.getBooks());
        basketService.clearBasketAfterOrder();
        return newOrderDetails;
    }
}
