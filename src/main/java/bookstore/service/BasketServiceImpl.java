package bookstore.service;

import bookstore.domain.Basket;
import bookstore.domain.Book;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BasketServiceImpl implements BasketService {

    /**
     * Initial methods...
     * When order is submitted - basket is saved in new table
     * and has entity relationship with User.
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

    }

    @Override
    public void removeBookFromBasket() {

    }

    @Override
    public void clearBasket() {

    }

}
