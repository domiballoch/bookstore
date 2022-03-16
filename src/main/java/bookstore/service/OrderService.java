package bookstore.service;

import bookstore.domain.Orders;
import bookstore.domain.Users;

import java.util.Optional;

public interface OrderService {

    Orders submitOrder(final Optional<Users> user);
}
