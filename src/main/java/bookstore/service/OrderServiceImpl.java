package bookstore.service;

import bookstore.dao.OrderRepository;
import bookstore.domain.Basket;
import bookstore.domain.Orders;
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
    private BasketService basketService;

    /**
     * Saves order to DB
     *
     * @param user
     * @return
     */
    @Override
    public Orders submitOrder(final Optional<Users> user) {
        log.info("Saving order: {} {}", user.toString(), basket);
        Orders order = Orders.builder()
                .totalItems(basket.size())
                .totalPrice(basketService.calculateBasket(basket))
                .orderDate(LocalDateTime.now())
                .build();
        orderRepository.save(order);
        log.info("Order complete: {} {}", order.toString(), user.toString());
        basketService.clearBasketAfterOrder();
        return order;
    }
}
