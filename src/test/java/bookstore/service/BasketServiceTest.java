package bookstore.service;

import bookstore.dao.OrderRepository;
import bookstore.domain.Basket;
import bookstore.domain.Book;
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

    @DisplayName("Should remove book from basket and increase stock")
    @Test
    public void shouldRemoveBookFromBasketAndIncreaseStock() {
        final Book book = CREATE_ONE_BOOK;
        //stock is 10
        book.setStock(10);
        bookService.addBookToBasket(book);

        assertThat(book.getStock()).isEqualTo(9);
        assertThat(basket.getBooks().size()).isEqualTo(1);

        basketService.removeBookFromBasket(book);

        assertThat(book.getStock()).isEqualTo(10);
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
        bookService.addBookToBasket(book1);
        bookService.addBookToBasket(book2);
        bookService.addBookToBasket(book3);
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
        bookService.addBookToBasket(book1);
        bookService.addBookToBasket(book2);
        bookService.addBookToBasket(book3);

        assertThat(basket.getBooks()).size().isEqualTo(3);
        assertThat(book1.getStock()).isEqualTo(9);
        assertThat(book2.getStock()).isEqualTo(9);
        assertThat(book3.getStock()).isEqualTo(9);

        basketService.clearBasket();

        assertThat(basket.getBooks()).size().isEqualTo(0);
        assertThat(book1.getStock()).isEqualTo(10);
        assertThat(book2.getStock()).isEqualTo(10);
        assertThat(book3.getStock()).isEqualTo(10);
    }
}
