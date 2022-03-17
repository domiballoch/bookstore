package bookstore.service;

import bookstore.domain.OrderDetails;

public interface OrderService {

    void submitOrder(final OrderDetails orderDetails);
}
