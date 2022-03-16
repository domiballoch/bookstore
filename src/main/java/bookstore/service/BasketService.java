package bookstore.service;

import bookstore.domain.Basket;
import bookstore.domain.Book;

import java.math.BigDecimal;

public interface BasketService {

    Basket getBasket();

    BigDecimal calculateBasket(final Basket basket);

    void removeBookFromBasket(final Book book);

    void clearBasketAfterOrder();

    void clearBasket();
}
