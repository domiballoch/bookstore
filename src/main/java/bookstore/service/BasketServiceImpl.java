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

    public Basket getBasket() {
        //show books and price calc
        calculateBasket(basket);
        return basket;
    }

    /**
     * Calculates total of basket
     *
     * @param basket
     * @return
     */
    @Override
    public BigDecimal calculateBasket(final Basket basket) {
        final BigDecimal totalPrice = basket.stream()
                                            .map(Book::getPrice)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPrice;
    }

    /**
     * Removes Book from Basket(List)
     * First adds removed Book(s) to separate list the compares against Basket
     * If Basket contains any then they get removed - avoiding ConcurrentModificationException of Stream
     *
     * @param book
     */
    @Override
    public void removeBookFromBasket(final Book book) {
        log.info("Removing book from basket: {}", book);
        List<Object> removedBooks = new ArrayList<>();

        basket.stream().forEach(b -> {
            b.equals(book);
            removedBooks.add(b);
        });

        if(CollectionUtils.containsAny(removedBooks, basket)) {
            basket.removeAll(removedBooks);
            log.info("Removed book(s) from basket: {}", removedBooks);
            book.setStock(book.getStock() +1);
        }
    }

    /**
     * Clears basket after order
     */
    public void clearBasketAfterOrder() {
        basket.clear();
        log.info("Basket cleared");
    }

    /**
     * Clears basket and resets stock
     */
    @Override
    public void clearBasket() {
        basket.forEach(book -> book.setStock(book.getStock()+1));
        basket.clear();
        log.info("Basket cleared and stock reset");
    }
}
