package bookstore.service;

import bookstore.domain.Users;

public interface OrderService {

    void submitOrder(final Users user);
}
