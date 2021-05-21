package bookstore.service;

import java.math.BigDecimal;

public interface BasketService {

    BigDecimal calculateBasket();

    void submitOrder();
}
