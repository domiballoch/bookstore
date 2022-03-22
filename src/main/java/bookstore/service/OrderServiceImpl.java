package bookstore.service;

import bookstore.dao.OrderRepository;
import bookstore.dao.UserRepository;
import bookstore.domain.Basket;
import bookstore.domain.OrderDetails;
import bookstore.domain.Users;
import bookstore.exception.BookstoreDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookStoreConstants.DATABASE_NOT_AVAILABLE;

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
     * Returns all orders
     *
     * @return
     */
    @Override
    public List<OrderDetails> findAllOrders() {
        log.info("Finding all orders");
        return orderRepository.findAll();
    }

    /**
     * Finds order by orderDetailsId
     *
     * @param orderDetailsId
     * @return
     */
    @Override
    public Optional<OrderDetails> findOrderById(final long orderDetailsId) {
        log.info("Finding order by orderDetailsId: {}", orderDetailsId);
        return Optional.ofNullable(orderRepository.findById(orderDetailsId)
                .orElseThrow(() -> new BookstoreDataException(DATABASE_NOT_AVAILABLE)));
    }

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

        log.info("Saving order details");
        OrderDetails newOrderDetails = OrderDetails.builder()
                .bookList(basket.getBooks())
                .users(user)
                .orderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)).build();

        orderRepository.save(newOrderDetails);
        log.info("Order complete: {}", newOrderDetails.toString());

        bookService.updateBookStock(basket.getBooks());
        basketService.clearBasketAfterOrder();
        return newOrderDetails;
    }
}
