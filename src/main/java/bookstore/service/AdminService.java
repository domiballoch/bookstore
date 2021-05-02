package bookstore.service;

import bookstore.domain.Book;
import bookstore.domain.Category;

import java.math.BigDecimal;

public interface AdminService {

    Book addNewBookToBookStore(final long isbn, final Category category, final String title,
                               final String author, final BigDecimal price);

    void deleteSingleBookFromBookstoreByIsbn(final long isbn);

    //Book amendBook(final long isbn, final Category category, final String title,
    //final String author, final BigDecimal price);

}
