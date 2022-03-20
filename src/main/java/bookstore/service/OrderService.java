package bookstore.service;

import bookstore.domain.OrderDetails;
import bookstore.domain.Users;

public interface OrderService {

    OrderDetails submitOrder(final Users user);
}
