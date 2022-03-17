package bookstore.service;

import bookstore.dao.OrderRepository;
import bookstore.domain.Basket;
import bookstore.domain.Book;
import bookstore.domain.OrderDetails;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static bookstore.utils.TestDataUtils.CREATE_ANOTHER_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_ONE_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_ONE_USER;
import static bookstore.utils.TestDataUtils.CREATE_YET_ANOTHER_BOOK;
import static bookstore.utils.TestDataUtils.returnOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Spy
    private Basket basket;

    @Spy
    private BasketService basketService;

    @Spy
    private BookService bookService;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Disabled //TODO:Fix not working
    @DisplayName("Should submit order")
    @Test
    public void shouldSubmitOrder() {
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

        final OrderDetails newOrder = returnOrder(basket.getBooks(), basketService.calculateBasket(basket), CREATE_ONE_USER);

        final BigDecimal totalPrice = (book1.getPrice().add(book2.getPrice().add(book3.getPrice())));
        //when(basketService.calculateBasket(any(Basket.class))).thenReturn(totalPrice);
        //when(orderService.submitOrder(Optional.of(CREATE_ONE_USER))).thenReturn(newOrder);
        //doNothing().when(orderService.submitOrder(any(OrderDetails.class)));
        when(orderRepository.save(any(OrderDetails.class))).thenReturn(newOrder);
        //doNothing().when(basketService).clearBasketAfterOrder();

        orderService.submitOrder(newOrder);

        //orderRepo -> findAll -> assert result
        //assertThat(result).isEqualTo(newOrder);

        verify(orderRepository, times(1)).save(any(OrderDetails.class));
    }
}
