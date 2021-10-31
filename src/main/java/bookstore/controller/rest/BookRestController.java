package bookstore.controller.rest;

import bookstore.domain.Book;
import bookstore.domain.Category;
import bookstore.exception.BookNotFoundException;
import bookstore.service.BookService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookConstants.BOOK_NOT_FOUND;

@RestController
@Slf4j
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
    public ResponseEntity<Optional<Book>> findBookByIsbn(@PathVariable final Long isbn) {
        final Optional<Book> book = Optional.ofNullable(bookService.findBookByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND)));
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

    public ResponseEntity<List<Book>> noResultsFound(List<Book> list, Object object) {
        log.info("No results found: {}", object);
        return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
    }
}
