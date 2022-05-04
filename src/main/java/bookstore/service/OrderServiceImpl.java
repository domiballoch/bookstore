package bookstore.service;

import bookstore.dao.OrderRepository;
import bookstore.dao.UserRepository;
import bookstore.domain.Basket;
import bookstore.domain.OrderDetails;
import bookstore.domain.Users;
import bookstore.exception.BookstoreBasketException;
import bookstore.exception.BookstoreValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookStoreConstants.BASKET_IS_EMPTY;
import static bookstore.utils.BookStoreConstants.INCORRECT_DETAILS;

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
        return orderRepository.findById(orderDetailsId);
    }

    /**
     * Saves orderDetails to DB
     * User checked if exist, if false then save details
     * Basket is added to orderDetails and attached to user with date
     *
     * @param user
     */
    @Transactional
    @Override
    public OrderDetails submitOrder(final Users user) {
        if(basket.getBooks().isEmpty()) {
            throw new BookstoreBasketException(BASKET_IS_EMPTY);
        }
        OrderDetails newOrderDetails = null;
        final Optional<Users> foundUser = userRepository.findById(user.getUserId());
        if(!foundUser.isPresent()) {
            userRepository.save(user);
            log.info("User not found so saving details {}", user.toString());
        } if(validateUser(Optional.of(user)) == false) {
                log.info("User details from search do not match stored records");
                throw new BookstoreValidationException(INCORRECT_DETAILS);
            } else {
            log.info("User found, saving order details {}", user.toString());
            newOrderDetails = OrderDetails.builder()
                    .orderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .users(user)
                    .bookList(basket.getBooks())
                    .build();

            //newOrderDetails.getBookList().forEach(book -> book.setIsbn(null));
            orderRepository.save(newOrderDetails);
            log.info("Order complete: {}", newOrderDetails.toString());
        }
        bookService.updateBookStock(basket.getBooks());
        basketService.clearBasketAfterOrder();
        return newOrderDetails;
    }

    private boolean validateUser(final Optional<Users> user) {
        final Optional<Users> foundUser = userRepository.findById(user.get().getUserId());
        return user.get().equals(foundUser.get()) ? true : false;
    }
}
