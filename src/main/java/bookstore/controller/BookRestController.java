package bookstore.controller;

import bookstore.domain.Book;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookConstants.BOOK_NOT_FOUND;

@RestController
@Slf4j
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/findAllBooks/rest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> findAllBooks() {
        final List<Book> bookList = bookService.findAllBooks();
        return new ResponseEntity<List<Book>>(bookList, HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping(value = "/findBook/rest/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Book>> findBookByIsbn(@PathVariable final long isbn) {
        final Optional<Book> book = Optional.ofNullable(bookService.findBookByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND)));
            return new ResponseEntity<Optional<Book>>(book, HttpStatus.OK);
    }

}
