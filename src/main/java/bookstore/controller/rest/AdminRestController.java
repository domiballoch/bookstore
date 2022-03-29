package bookstore.controller.rest;

import bookstore.domain.Book;
import bookstore.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/addNewBook")
    public ResponseEntity<Book> addNewBookToBookstore(@RequestBody final Book book) {
        final Book addedBook = adminService.addNewBookToBookstoreJson(book);
        return new ResponseEntity<>(addedBook, HttpStatus.OK); //Http.Status.CREATED?
    }

    @DeleteMapping(value = "/deleteBook/{isbn}")
    public ResponseEntity<Long> deleteBookFromBookstore(@PathVariable final long isbn) {
        adminService.deleteBookFromBookstoreByIsbn(isbn);
        return new ResponseEntity<>(isbn, HttpStatus.OK);
    }

    //Using PUT for idempotency - resending the whole Entity
    @PutMapping(value = "/updateBook/{isbn}")
    public ResponseEntity<Book> updateBookInBookstore(@RequestBody final Book book, @PathVariable final long isbn) {
        final Book updatedBook = adminService.updateBookInBookstoreJson(book, isbn);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }
}
