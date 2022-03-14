package bookstore.service;

import bookstore.domain.Basket;
import bookstore.domain.Book;
import bookstore.domain.Orders;
import bookstore.domain.Users;

import java.math.BigDecimal;
import java.util.Optional;

public interface BasketService {

    Basket getBasket();

    BigDecimal calculateBasket(final Basket basket);

    Orders submitOrder(final Optional<Users> user);

    void removeBookFromBasket(final Book book);

    void clearBasketAfterOrder();

    void clearBasket();
}
