package bookstore.dao;

import bookstore.domain.Basket;
import bookstore.domain.Book;
import bookstore.domain.Category;
import bookstore.domain.OrderDetails;
import bookstore.domain.Users;
import bookstore.service.BasketService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static bookstore.utils.TestDataUtils.CREATE_ANOTHER_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_ONE_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_YET_ANOTHER_BOOK;
import static bookstore.utils.TestDataUtils.returnOneOrder;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Uses HSQLDB in-memory db
 */
@Sql(scripts = {"classpath:test_data/orderRepositoryTest.sql"})
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Slf4j
public class OrderRepositoryIT {

    @Spy
    private Basket basket;

    @Spy
    private BasketService basketService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Test //TODO:Fix this test
    public void findOrderById() {
        final Optional<OrderDetails> orderDetails = orderRepository.findById(3001L);
        log.info("ORDER: ", orderDetails.get().getOrderDate());

        assertThat(orderDetails.get().getOrderDetailsId()).isEqualTo(3001);
        assertThat(orderDetails.get().getBookList().size()).isEqualTo(1);
        assertThat(orderDetails.get().getOrderDate()).isEqualTo(LocalDateTime.of(
                2022, Month.MARCH, 01, 9, 00, 00));

        assertThat(orderDetails.get().getBookList().get(0).getIsbn()).isEqualTo(1001);
        assertThat(orderDetails.get().getBookList().get(0).getTitle()).isEqualTo("Tall Tales");
        assertThat(orderDetails.get().getBookList().get(0).getAuthor()).isEqualTo("Mr Fredrikson");
        assertThat(orderDetails.get().getBookList().get(0).getCategory()).isEqualTo(Category.FICTION);
        assertThat(orderDetails.get().getBookList().get(0).getPrice()).isEqualTo(4.99);
        assertThat(orderDetails.get().getBookList().get(0).getStock()).isEqualTo(25);

        assertThat(orderDetails.get().getUsers().getUserId()).isEqualTo("2001");
        assertThat(orderDetails.get().getUsers().getFirstName()).isEqualTo("John");
        assertThat(orderDetails.get().getUsers().getLastName()).isEqualTo("Smith");
        assertThat(orderDetails.get().getUsers().getAddressLine1()).isEqualTo("10 Something Road");
        assertThat(orderDetails.get().getUsers().getAddressLine2()).isEqualTo("London");
        assertThat(orderDetails.get().getUsers().getPostCode()).isEqualTo("SW1");
    }

    @Test
    public void findAllOrders() {
        final List<OrderDetails> orderDetails = orderRepository.findAll();

        assertThat(orderDetails.size()).isEqualTo(5);
    }

    @Test
    public void saveOrder() {
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

        final Users newUser = Users.builder()
                .userId(2001L)
                .firstName("John")
                .lastName("Smith")
                .addressLine1("10 Something Road")
                .addressLine2("London")
                .postCode("SW1")
                .build();

        final OrderDetails newOrder = returnOneOrder(basket.getBooks(), newUser);
        newOrder.setOrderDetailsId(3006L);
        final OrderDetails result = orderRepository.save(newOrder);

        assertThat(result).isEqualTo(newOrder);
    }
}
