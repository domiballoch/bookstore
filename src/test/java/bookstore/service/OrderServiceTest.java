package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.dao.OrderRepository;
import bookstore.dao.UserRepository;
import bookstore.domain.Basket;
import bookstore.domain.Book;
import bookstore.domain.OrderDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static bookstore.utils.TestDataUtils.CREATE_ANOTHER_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_ONE_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_ONE_ORDER;
import static bookstore.utils.TestDataUtils.CREATE_ONE_USER;
import static bookstore.utils.TestDataUtils.CREATE_YET_ANOTHER_BOOK;
import static bookstore.utils.TestDataUtils.ORDERLIST;
import static bookstore.utils.TestDataUtils.populateOrderData;
import static bookstore.utils.TestDataUtils.returnOneOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Spy
    private Basket basket;

    @Mock
    private BasketServiceImpl basketService;

    @Mock
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void prepareData() {
        populateOrderData();
    }

    @DisplayName("Should return all orders")
    @Test
    public void shouldReturnAllBooks() {
        when(orderRepository.findAll()).thenReturn(ORDERLIST);
        final List<OrderDetails> result = orderService.findAllOrders();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(ORDERLIST);
        verify(orderRepository, times(1)).findAll();
    }

    @DisplayName("Should return empty list")
    @Test
    public void shouldReturnEmptyList() {
        final List<OrderDetails> emptyList = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(emptyList);
        final List<OrderDetails> result = orderService.findAllOrders();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(Collections.EMPTY_LIST);
        verify(orderRepository, times(1)).findAll();
    }

    @DisplayName("Should return one order by id")
    @Test
    public void shouldReturnOneBookByIsbn(){
        final long orderDetailsId = 4;
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of(CREATE_ONE_ORDER));
        final Optional<OrderDetails> result = orderService.findOrderById(orderDetailsId);

        assertThat(result).isEqualTo(Optional.of(CREATE_ONE_ORDER));
        verify(orderRepository, times(1)).findById(any(Long.class));
    }

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
        basket.addBook(book1);
        basket.addBook(book2);
        basket.addBook(book3);

        assertThat(basket.getBooks()).size().isEqualTo(3);

        final OrderDetails newOrder = returnOneOrder(basket.getBooks(), CREATE_ONE_USER);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(CREATE_ONE_USER));
        when(orderRepository.save(any(OrderDetails.class))).thenReturn(newOrder);

        final OrderDetails result = orderService.submitOrder(CREATE_ONE_USER);

        assertThat(result.getBookList()).isEqualTo(newOrder.getBookList());
        assertThat(result.getUsers()).isEqualTo(newOrder.getUsers());
        assertThat(result.getOrderDate()).isEqualTo(newOrder.getOrderDate());
        verify(orderRepository, times(1)).save(any(OrderDetails.class));
    }
}
