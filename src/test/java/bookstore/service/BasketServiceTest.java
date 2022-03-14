package bookstore.service;

import bookstore.dao.OrderRepository;
import bookstore.domain.Basket;
import bookstore.domain.Book;
import bookstore.domain.Orders;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static bookstore.utils.TestDataUtils.CREATE_ANOTHER_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_ONE_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_ONE_USER;
import static bookstore.utils.TestDataUtils.CREATE_YET_ANOTHER_BOOK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        assertThat(basket.size()).isEqualTo(1);

        basketService.removeBookFromBasket(book);

        assertThat(book.getStock()).isEqualTo(10);
        assertThat(basket.size()).isEqualTo(0);
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

        assertThat(basket).size().isEqualTo(3);
        assertThat(book1.getStock()).isEqualTo(9);
        assertThat(book2.getStock()).isEqualTo(9);
        assertThat(book3.getStock()).isEqualTo(9);

        basketService.clearBasket();

        assertThat(basket).size().isEqualTo(0);
        assertThat(book1.getStock()).isEqualTo(10);
        assertThat(book2.getStock()).isEqualTo(10);
        assertThat(book3.getStock()).isEqualTo(10);
    }

    //---------------------------------------------
    //TODO:Need to amend this test -
    //TODO:Move to orderServiceTest, use mocks and change the design to include orderDetails
    @Disabled
    @DisplayName("Should submit order")
    @Test
    public void shouldSubmitOrder() {
        final Book book1 = CREATE_ONE_BOOK;
        final Book book2 = CREATE_ANOTHER_BOOK;
        final Book book3 = CREATE_YET_ANOTHER_BOOK;
        book1.setStock(10);
        book2.setStock(10);
        book3.setStock(10);
//        bookService.addBookToBasket(book1);
//        bookService.addBookToBasket(book2);
//        bookService.addBookToBasket(book3);

        //final Basket basket = basketService.getBasket();
        basket.add(book1);
        basket.add(book2);
        basket.add(book3);
        assertThat(basket).size().isEqualTo(3);

        final Orders newOrder = Orders.builder()
                .orderId(1L)
                .totalItems(basket.size())
                .totalPrice(basketService.calculateBasket(basket))
                .orderDate(LocalDateTime.now())
                .build();

        final BigDecimal totalPrice = (book1.getPrice().add(book2.getPrice().add(book3.getPrice())));
        //when(basketService.calculateBasket(any(Basket.class))).thenReturn(totalPrice);
        when(basketService.submitOrder(any(Optional.class))).thenReturn(newOrder);
        when(orderRepository.save(any(Orders.class))).thenReturn(newOrder);

        final Orders result = basketService.submitOrder(Optional.ofNullable(CREATE_ONE_USER));

        assertThat(result.getBooksList()).isEqualTo(basket);
        assertThat(result).isEqualTo(newOrder);
        assertThat(book1.getStock()).isEqualTo(9);//others?
        verify(orderRepository, times(1)).save(any(Orders.class));
    }
}
