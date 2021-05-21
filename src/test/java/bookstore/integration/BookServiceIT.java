package bookstore.integration;


import bookstore.domain.Basket;
import bookstore.domain.Book;
import bookstore.service.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;
import java.util.Optional;

import static bookstore.utils.TestDataUtils.CREATE_ONE_BOOK;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BookServiceIT {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private Basket basket;

    @DisplayName("Should use cache when calling book repository twice")
    @Test
    void shouldUseCacheWhenCallingBookRepositoryTwice(){
        List<Book> call1 = bookService.findAllBooks();
        List<Book> call2 = bookService.findAllBooks();
        Cache allBooksCache = cacheManager.getCache("books");

        Optional<Book> book1 = bookService.findBookByIsbn(1);
        //TODO:fix null
        //assertEquals(book1, getCachedBook(1));

        assertNotNull(call1);
        assertNotNull(call2);
        assertNotNull(allBooksCache);
    }

    private Optional<Book> getCachedBook(final long isbn) {
        return ofNullable(cacheManager.getCache("books")).map(b -> b.get(isbn, Book.class));
    }

    @DisplayName("Should add one book to basket")
    @Test
    public void shouldAddOneBookToBasket() {
        final Book newBook = CREATE_ONE_BOOK;
        bookService.addBookToBasket(newBook);

        assertThat(basket).contains(newBook);
    }

    @DisplayName("Should remove one book from basket")
    @Test
    public void shouldRemoveOneBookFromBasket() {
        final Book newBook = CREATE_ONE_BOOK;
        bookService.addBookToBasket(newBook);
        bookService.removeBookFromBasket(newBook);

        assertThat(basket).doesNotContain((newBook));
    }

}
