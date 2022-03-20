package bookstore.service;

import bookstore.domain.OrderDetails;
import bookstore.domain.Users;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderDetails> findAllOrders();

    Optional<OrderDetails> findOrderById(final long orderDetailsId);

    OrderDetails submitOrder(final Users user);
}
