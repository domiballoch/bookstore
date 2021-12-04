package bookstore.service;

import bookstore.domain.Basket;

import java.math.BigDecimal;

public interface BasketService {

    BigDecimal calculateBasket(final Basket basket);

    void submitOrder();

    void removeBookFromBasket();

    void clearBasket();
}
