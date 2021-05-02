package bookstore.service;

import bookstore.domain.Book;
import bookstore.domain.Category;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookService {

    //-----user

    List<Book> findAllBooks();

    Optional<Book> findBookByIsbn(final long isbn);

    int getBookStock(final String title, final String Author);

    boolean inStock(final String title, final String Author);

    List<Book> findBooksByCategory(final Category category);

    List<Book> findBookBySearchTerm(final String search);

    //-----admin

    Book addNewBookToBookStore(final long isbn, final Category category, final String title,
                               final String author, final BigDecimal price);

    //Book amendBook(final long isbn, final Category category, final String title,
                   //final String author, final BigDecimal price);

    void deleteSingleBookFromBookstoreByIsbn(final long isbn);

    void deleteBookByTypeFromBookStoreByIsbn(final long isbn);

}
