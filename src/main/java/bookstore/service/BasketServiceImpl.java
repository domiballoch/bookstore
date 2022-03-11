package bookstore.service;

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
    private Basket basket;

    /**
     * Initial methods...
     * When order is submitted - basket is saved in new table
     * and has entity relationship with User.
     */

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

    @Override
    public void submitOrder() {
        //orderRepo.save purchaseDTO
        //update book.Repo stock
        //log.info("Items ordered: {}, basket)
        //clearBasket();
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
     * Clears basket
     */
    @Override
    public void clearBasket() {
        basket.clear();
        log.info("Basket cleared");
    }

}
