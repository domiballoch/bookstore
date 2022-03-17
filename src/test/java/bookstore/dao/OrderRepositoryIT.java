package bookstore.dao;

import bookstore.domain.Basket;
import bookstore.domain.Book;
import bookstore.domain.OrderDetails;
import bookstore.service.BasketService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static bookstore.utils.TestDataUtils.CREATE_ANOTHER_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_ONE_BOOK;
import static bookstore.utils.TestDataUtils.CREATE_ONE_USER;
import static bookstore.utils.TestDataUtils.CREATE_YET_ANOTHER_BOOK;
import static bookstore.utils.TestDataUtils.returnOrder;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Uses HSQLDB in-memory db
 */
@Sql(scripts = {"classpath:test_data/orderRepositoryTest.sql"})
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderRepositoryIT {

    @Spy
    private Basket basket;

    @Spy
    private BasketService basketService;

    @Autowired
    private OrderRepository orderRepository;

    @Disabled
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

        final OrderDetails newOrder = returnOrder(basket.getBooks(), basketService.calculateBasket(basket), CREATE_ONE_USER);

        final OrderDetails result = orderRepository.save(newOrder);

        assertThat(result).isEqualTo(newOrder);
    }
}
