package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.domain.Book;
import bookstore.domain.Category;
import bookstore.exception.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static bookstore.utils.BookConstants.BOOK_NOT_FOUND;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Creates new Book object and saves to Bookstore DB(Book table)
     * Increases Stock of Book
     *
     * @param - isbn
     * @param - category
     * @param - title
     * @param - author
     * @param - price
     * @return - Book object
     */
    @Override
    public Book addNewBookToBookStore(final long isbn, final Category category, final String title,
                                      final String author, final BigDecimal price, final int stock) {
        log.info("Adding new book to bookstore");
        final Book newBook = Book.builder()
                .isbn(isbn)
                .category(category)
                .title(title)
                .author(author)
                .price(price)
                .stock(stock)
                .build();
        bookRepository.save(newBook);

        //TODO: Need to increase stock of book per isbn in many to one
        log.info("New book added to bookstore: {}", newBook.toString());
        return newBook;
    }
    
    @Override
    public Book addNewBookToBookStoreWeb(final Book book) {
        log.info("Adding new book to bookstore");
        final Book newBook = Book.builder()
                .isbn(book.getIsbn())
                .category(book.getCategory())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .stock(book.getStock())
                .build();
        bookRepository.save(newBook);

        //TODO: Need to increase stock of book per isbn
        log.info("New book added to bookstore: {}", newBook.toString());
        return newBook;
    }

    /**
     * Deletes single Book from Bookstore DB(Book table)
     * Decreases Stock of Book if already exists
     *
     * @param isbn
     */
    @Override
    public void deleteSingleBookFromBookstoreByIsbn(final long isbn) {
        log.info("Deleting book from bookstore by isbn: {}", isbn);
        try {
        bookRepository.deleteById(isbn);
        } catch(BookNotFoundException e) {
        	log.warn(BOOK_NOT_FOUND);
        }
        log.info("Deleted book from bookstore by isbn: {}", isbn);
    }   

    @Override
    public void updateBook(final Book book) {
        log.info("Updating book: {}", book.toString());
        bookRepository.delete(book);
        bookRepository.save(book);
    }

}
