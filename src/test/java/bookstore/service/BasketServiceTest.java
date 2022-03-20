package bookstore.service;

import bookstore.dao.OrderRepository;
import bookstore.domain.Basket;
import bookstore.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static bookstore.utils.TestDataUtils.CREATE_ANOTHER_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_ONE_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_YET_ANOTHER_BOOK;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BasketServiceTest {

    @Autowired
    private BasketServiceImpl basketService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private Basket basket;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    private void init() {
        basket.getBooks().clear();
    }

    @DisplayName("Should remove book from basket and increase stock")
    @Test
    public void shouldRemoveBookFromBasketAndIncreaseStock() {
        final Book book = CREATE_ONE_BOOK;
        book.setStock(10);
        basket.addBook(book);

        assertThat(basket.getBooks().size()).isEqualTo(1);

        basketService.removeBookFromBasket(book);

        assertThat(book.getStock()).isEqualTo(11);
        assertThat(basket.getBooks().size()).isEqualTo(0);
    }

    @DisplayName("Should sum total price of basket")
    @Test
    public void shouldSumTotalPriceOfBasket() {
        final Book book1 = CREATE_ONE_BOOK;
        final Book book2 = CREATE_ANOTHER_BOOK;
        final Book book3 = CREATE_YET_ANOTHER_BOOK;
        book1.setStock(10);
        book2.setStock(10);
        book3.setStock(10);
        basket.addBook(book1);
        basket.addBook(book2);
        basket.addBook(book3);

        final BigDecimal totalPrice = basketService.calculateBasket(basket);

        assertThat(totalPrice).isEqualByComparingTo(new BigDecimal("88.67"));
    }

    @DisplayName("Should clear basket")
    @Test
    public void shouldClearBasket() {
        final Book book1 = CREATE_ONE_BOOK;
        final Book book2 = CREATE_ANOTHER_BOOK;
        final Book book3 = CREATE_YET_ANOTHER_BOOK;
        book1.setStock(10);
        book2.setStock(10);
        book3.setStock(10);
        basket.addBook(book1);
        basket.addBook(book2);
        basket.addBook(book3);

        assertThat(basket.getBooks()).size().isEqualTo(3);

        basketService.clearBasket();

        assertThat(basket.getBooks()).size().isEqualTo(0);
        assertThat(book1.getStock()).isEqualTo(11);
        assertThat(book2.getStock()).isEqualTo(11);
        assertThat(book3.getStock()).isEqualTo(11);
    }
}
