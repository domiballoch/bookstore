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
     * Saves order details to DB
     * Populated basket(books), total price, dateTime, user details added to orderDetails
     * user details checked if exist, if false then save details
     *
     * @param user
     */
    @Override
    public void submitOrder(final Users user) {
        final Optional<Users> foundUser = userRepository.findById(user.getUserId());
        foundUser.ifPresent(u -> {
            userRepository.save(u);
            log.info("User not found so saving details {}", user.toString());
        });

        log.info("Saving order details: {}", user.toString());
        OrderDetails newOrderDetails = OrderDetails.builder()
                .bookList(basket.getBooks())
                .totalPrice(basketService.calculateBasket(basket))
                .orderDate(LocalDateTime.now())
                .user(user).build();

        orderRepository.save(newOrderDetails);
        log.info("Order complete: {}", newOrderDetails.toString());

        bookService.updateBookStock(basket.getBooks());
        basketService.clearBasketAfterOrder();
    }
}
