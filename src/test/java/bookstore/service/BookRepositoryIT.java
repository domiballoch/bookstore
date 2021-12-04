package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.domain.Book;
import bookstore.domain.Category;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Uses HSQLDB in-memory db
 */
@Sql(scripts = {"classpath:test_data/bookRepositoryTest.sql"})
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryIT {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findBookById() {
        final Optional<Book> book = bookRepository.findById(1001L);

        assertThat(book.get().getIsbn()).isEqualTo(1001);
        assertThat(book.get().getTitle()).isEqualTo("Tall Tales");
        assertThat(book.get().getAuthor()).isEqualTo("Mr Fredrikson");
        assertThat(book.get().getCategory()).isEqualTo(Category.FICTION);
        assertThat(book.get().getPrice()).isEqualTo(BigDecimal.valueOf(4.99));
        assertThat(book.get().getStock()).isEqualTo(25);
    }

    @Test
    public void findAllBooks() {
        final List<Book> books = bookRepository.findAll();

        assertThat(books.size()).isEqualTo(5);
    }

    @Test
    public void findBookBySearchTermIgnoreCase_OneResult() {
        final List<Book> books = bookRepository.findBookBySearchTermIgnoreCase("English");

        assertThat(books.get(0).getTitle()).isEqualTo("An English Rose");
    }

    @Test
    public void findBookBySearchTermIgnoreCase_TwoResults() {
        final List<Book> books = bookRepository.findBookBySearchTermIgnoreCase("Tales");

        assertThat(books.size()).isEqualTo(2);
        assertThat(books.get(0).getTitle()).isEqualTo("Tall Tales");
        assertThat(books.get(1).getTitle()).isEqualTo("Short Tales");
    }

    @Disabled //TODO:fix
    @Test
    public void findBookByCategory_OneResult() {
        final List<Book> books = bookRepository.findBooksByCategory(Category.HORROR);

        assertThat(books.get(0).getTitle()).isEqualTo("The Crab Man");
    }

    @Disabled //TODO:fix
    @Test
    public void findBookByCategory_TwoResults() {
        final List<Book> books = bookRepository.findBooksByCategory(Category.FICTION);

        assertThat(books.size()).isEqualTo(2);
        assertThat(books.get(0).getTitle()).isEqualTo("Tall Tales");
        assertThat(books.get(1).getTitle()).isEqualTo("Short Tales");
    }

    @Disabled //TODO:fix
    @Test
    public void getBookStock() {
        final int stock = bookRepository.getBookStock("A Foreign Land", "Mr Kite");

        assertThat(stock).isEqualTo(10);
    }

    @Disabled //TODO:fix
    @Test
    public void findByTitleAndAuthor() {
        final List<Book> books = bookRepository.findByTitleAndAuthor("A Foreign Land", "Mr Kite");

        assertThat(books.get(0).getTitle()).isEqualTo("A Foreign Land");
        assertThat(books.get(0).getAuthor()).isEqualTo("Mr Kite");
    }

    @Test
    public void saveOneBook() {

    }

    @Test
    public void deleteOneBook() {

    }

    @Test
    public void updateOneBook() {

    }

}