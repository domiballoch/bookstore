package bookstore.service;

import bookstore.domain.Book;
import bookstore.domain.Category;

import java.math.BigDecimal;

import javax.validation.Valid;

public interface AdminService {

    Book addNewBookToBookStore(final long isbn, final Category category, final String title,
                               final String author, final BigDecimal price, final int stock);
    
    Book addNewBookToBookStoreWeb(final Book book);

    void deleteSingleBookFromBookstoreByIsbn(final long isbn);

	void updateBook(final Book book);

    //Book amendBook(final long isbn, final Category category, final String title,
    //final String author, final BigDecimal price);

}
