package bookstore.controller.rest;

import bookstore.domain.Book;
import bookstore.domain.Category;
import bookstore.exception.BookstoreNotFoundException;
import bookstore.service.BookService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookStoreConstants.BOOK_NOT_FOUND;

@Slf4j
@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/findAllBooks")
    public ResponseEntity<List<Book>> findAllBooks() {
        final List<Book> bookList = bookService.findAllBooks();
        if(bookList.isEmpty()) {
            noResultsFound(bookList, "All books");
        }
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping(value = "/findBook/{isbn}")
    public ResponseEntity<Optional<Book>> findBookByIsbn(@PathVariable final long isbn) {
        final Optional<Book> book = Optional.ofNullable(bookService.findBookByIsbn(isbn)
                .orElseThrow(() -> new BookstoreNotFoundException(BOOK_NOT_FOUND, isbn)));
            return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping(value = "/search/{search}")
    public ResponseEntity<List<Book>> findBooksBySearchTerm(@PathVariable final String search) {
        final List<Book> results = bookService.findBookBySearchTermIgnoreCase(search);
        if(results.isEmpty()) {
            noResultsFound(results, search);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping(value = "/category/{category}")
    public ResponseEntity<List<Book>> findBooksByCategory(@PathVariable final Category category) {
        final List<Book> results = bookService.findBooksByCategory(category);
        if(results.isEmpty()) {
            noResultsFound(results, category);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping(value = "/addBookToBasket/{isbn}")
    public ResponseEntity<List<Book>> addBookToBasket(@PathVariable final long isbn) {
        final List<Book> basket = bookService.addBookToBasket(isbn);
        return new ResponseEntity<>(basket, HttpStatus.OK);
    }

    @GetMapping(value = "/getBookstock/{isbn}")
    public ResponseEntity<Integer> getBookstock(@PathVariable final long isbn) {
        final int stock = bookService.getBookStock(isbn);
        return new ResponseEntity(stock, HttpStatus.OK);
        }

    private <T>  ResponseEntity<T> noResultsFound(final T t, final T r) {
        log.info("No results found: {}", r);
        return new ResponseEntity<T>(t, HttpStatus.NOT_FOUND);
    }
}

