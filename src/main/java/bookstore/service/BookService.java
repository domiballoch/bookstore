package bookstore.service;

import bookstore.domain.Book;
import bookstore.domain.Category;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAllBooks();

    Optional<Book> findBookByIsbn(final long isbn);
    
    Book findBookByIsbnWeb(final long isbn);

    int getBookStock(final String title, final String Author);

    boolean inStock(final String title, final String Author);

    List<Book> findBooksByCategory(final Category category);

    List<Book> findBookBySearchTermIgnoreCase(final String search);

    void addBookToBasket(final Book book);

}
