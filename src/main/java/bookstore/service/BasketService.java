package bookstore.service;

import bookstore.domain.Book;

import java.math.BigDecimal;
import java.util.List;

public interface BasketService {

    List<Book> getBasket();

    BigDecimal calculateBasket(final List<Book> bookList);

    List<Book> removeBookFromBasket(final long isbn);

    void clearBasketAfterOrder();

    void clearBasket();
}
