package bookstore.controller;

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
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(value = "/addNewBookToBookstore/rest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> addNewBookToBookstore(@PathVariable final long isbn, @PathVariable
    final Category category, @PathVariable final String title, @PathVariable
                                                      final String author, @PathVariable final BigDecimal price) {
        adminService.addNewBookToBookStore(isbn, category, title, author, price);
        return new ResponseEntity<Book>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteBookFromBookstore/rest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> deleteBookFromBookstore(@PathVariable final long isbn) {
        adminService.deleteSingleBookFromBookstoreByIsbn(isbn);
        return new ResponseEntity<Book>(HttpStatus.OK);
    }

//    @PatchMapping(value = "/amendBookInBookstore/rest", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Book> amendBookInBookstore() {
//        bookService.amendBookInBookstore();
//        return new ResponseEntity<Book>(HttpStatus.OK);
//    }
}
