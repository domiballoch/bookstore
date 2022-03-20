package bookstore.service;

import bookstore.dao.OrderRepository;
import bookstore.dao.UserRepository;
import bookstore.domain.Basket;
import bookstore.domain.Book;
import bookstore.domain.OrderDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @DisplayName("Should submit order")
    @Test
    public void shouldSubmitOrder() {
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

        final OrderDetails newOrder = returnOrder(basket.getBooks(), CREATE_ONE_USER, basketService.calculateBasket(basket));

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(CREATE_ONE_USER));
        when(orderRepository.save(any(OrderDetails.class))).thenReturn(newOrder);

        final OrderDetails result = orderService.submitOrder(CREATE_ONE_USER);

        assertThat(result.getBookList()).isEqualTo(newOrder.getBookList());
        assertThat(result.getUsers()).isEqualTo(newOrder.getUsers());
        assertThat(result.getTotalPrice()).isEqualTo(newOrder.getTotalPrice());
        assertThat(result.getOrderDate()).isEqualTo(newOrder.getOrderDate());
        verify(orderRepository, times(1)).save(any(OrderDetails.class));
    }
}
