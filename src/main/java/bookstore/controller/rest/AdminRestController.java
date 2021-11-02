package bookstore.controller.rest;

import bookstore.domain.Book;
import bookstore.domain.Category;
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

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/rest",
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/addNewBookToBookstore")
    public ResponseEntity<Book> addNewBookToBookstore(@PathVariable final long isbn, @PathVariable
    final Category category, @PathVariable final String title, @PathVariable
                    final String author, @PathVariable final BigDecimal price, final int stock) {
        adminService.addNewBookToBookStore(isbn, category, title, author, price, stock);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/addNewBookToBookstore2")
    public ResponseEntity<Book> addNewBookToBookstore2(@RequestBody final Book book) {
        adminService.addNewBookToBookStore2(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteBookFromBookstore")
    public ResponseEntity<Book> deleteBookFromBookstore(@PathVariable final long isbn) {
        adminService.deleteSingleBookFromBookstoreByIsbn(isbn);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Using PUT for idempotency - resending the whole Entity
    @PutMapping(value = "/updateBookInBookstore")
    public ResponseEntity<Book> updateBookInBookstore(@PathVariable final long isbn, @PathVariable
    final Category category, @PathVariable final String title, @PathVariable
                    final String author, @PathVariable final BigDecimal price, final int stock) {
        adminService.updateBook(isbn, category, title, author, price, stock);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PatchMapping(value = "/amendBookInBookstore", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Book> amendBookInBookstore() {
//        bookService.amendBookInBookstore();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
