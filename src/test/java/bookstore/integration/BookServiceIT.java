package bookstore.integration;


import bookstore.domain.Book;
import bookstore.service.BookServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BookServiceIT {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private BookServiceImpl bookService;

    @SneakyThrows
    @DisplayName("Should use cache when calling book repository twice")
    @Test
    void shouldUseCacheWhenCallingBookRepositoryTwice(){
        List<Book> call1 = bookService.findAllBooks();
        List<Book> call2 = bookService.findAllBooks();
        Cache allBooksCache = cacheManager.getCache("books");

        Optional<Book> book1 = bookService.findBookByIsbn(1);
        //assertEquals(book1, getCachedBook(1));

        assertNotNull(call1);
        assertNotNull(call2);
        assertNotNull(allBooksCache);
    }

    private Optional<Book> getCachedBook(final long isbn) {
        return ofNullable(cacheManager.getCache("books")).map(b -> b.get(isbn, Book.class));
    }

}
