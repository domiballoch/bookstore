package bookstore.integration;

import bookstore.domain.Basket;
import bookstore.service.BasketServiceImpl;
import bookstore.service.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static bookstore.utils.TestDataUtils.CREATE_ONE_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_ANOTHER_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_YET_ANOTHER_BOOK;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BasketServiceIT {

    @Autowired
    private BasketServiceImpl basketService;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private Basket basket;

    @Test
    public void shouldSumTotalPriceOfBasket() {
        bookService.addBookToBasket(CREATE_ONE_BOOK);
        bookService.addBookToBasket(CREATE_ANOTHER_BOOK);
        bookService.addBookToBasket(CREATE_YET_ANOTHER_BOOK);
        final BigDecimal totalPrice = basketService.calculateBasket(basket);

        assertThat(totalPrice).isEqualByComparingTo(new BigDecimal("88.67"));
    }
}
