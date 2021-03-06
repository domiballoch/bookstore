package bookstore.service;

import bookstore.domain.Book;
import bookstore.domain.Category;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAllBooks();

    Optional<Book> findBookByIsbn(final long isbn);
    
    Book findBookByIsbnWeb(final long isbn);

    int getBookStock(final long isbn);

    boolean inStock(final long isbn);

    List<Book> findBooksByCategory(final Category category);

    List<Book> findBookBySearchTermIgnoreCase(final String search);

    List<Book> addBookToBasket(final long isbn);

    void updateBookStock(final List<Book> books);

}
