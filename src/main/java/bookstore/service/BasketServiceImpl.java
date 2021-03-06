package bookstore.service;

import bookstore.dao.OrderRepository;
import bookstore.dao.UserRepository;
import bookstore.domain.Basket;
import bookstore.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Basket basket;

    public List<Book> getBasket() {
        return basket.getBooks();
    }

    /**
     * Calculates total of basket
     *
     * @param bookList
     * @return
     */
    @Override
    public BigDecimal calculateBasket(List<Book> bookList) {
        bookList = basket.getBooks();
        final BigDecimal totalPrice = bookList.stream()
                                            .map(Book::getPrice)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPrice;
    }

    /**
     * Removes Book from Basket(List)
     * First adds removed Book(s) to separate list the compares against Basket
     * If Basket contains any then they get removed - avoiding ConcurrentModificationException of Stream
     *
     * @param isbn
     */
    @Override
    public List<Book> removeBookFromBasket(final long isbn) {
        log.info("Removing book from basket: {}", isbn);
        List<Book> removedBooks = new ArrayList<>();

        basket.getBooks().forEach(b -> {
            b.getIsbn().equals(isbn);
            removedBooks.add(b);
        });

        if(CollectionUtils.containsAny(removedBooks, basket.getBooks())) {
            basket.getBooks().removeAll(removedBooks);
            log.info("Removed book(s) from basket: {}", removedBooks);
            removedBooks.forEach(b -> b.setStock(b.getStock() +1));
        }
        return basket.getBooks();
    }

    /**
     * Clears basket after order
     */
    public void clearBasketAfterOrder() {
        basket.getBooks().clear();
        log.info("Basket cleared");
    }

    /**
     * Clears basket and resets stock
     */
    @Override
    public void clearBasket() {
        basket.getBooks().forEach(book -> book.setStock(book.getStock()+1));
        basket.getBooks().clear();
        log.info("Basket cleared and stock reset");
    }
}
