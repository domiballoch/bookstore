package bookstore.service;

import bookstore.domain.Basket;
import bookstore.domain.Book;

import java.math.BigDecimal;

public interface BasketService {

    BigDecimal calculateBasket(final Basket basket);

    void submitOrder();

    void removeBookFromBasket(final Book book);

    void clearBasket();
}
