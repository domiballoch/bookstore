package bookstore.service;

import bookstore.domain.Book;
import bookstore.domain.Category;

import java.math.BigDecimal;

public interface AdminService {

    Book addNewBookToBookStore(final long isbn, final Category category, final String title,
                               final String author, final BigDecimal price, final int stock);

    Book addNewBookToBookStore2(final Book book);

    Book addNewBookToBookStoreWeb(final Book book);

    void deleteSingleBookFromBookstoreByIsbn(final long isbn);

	void updateBookWeb(final Book book);

    void updateBook(final long isbn, final Category category, final String title, final String author,
                    final BigDecimal price, final int stock);

}
