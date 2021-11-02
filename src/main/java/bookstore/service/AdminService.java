package bookstore.service;

import bookstore.domain.Book;
import bookstore.domain.Category;

import java.math.BigDecimal;

public interface AdminService {

    Book addNewBookToBookStorePathVariable(final long isbn, final Category category, final String title,
                               final String author, final BigDecimal price, final int stock);

    Book addNewBookToBookstoreJsonRequest(final Book book);

    Book addNewBookToBookStoreWeb(final Book book);

    void deleteSingleBookFromBookstoreByIsbn(final long isbn);

	void updateBookWeb(final Book book);

    void updateBook(final Category category, final String title, final String author,
                    final BigDecimal price, final int stock);

}
