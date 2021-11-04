package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.domain.Book;
import bookstore.exception.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static bookstore.utils.BookConstants.BOOK_NOT_FOUND;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Adds new book to bookstore by Json request
     *
     * @param book
     * @return book
     */
    @Override
    public Book addNewBookToBookstoreJson(final Book book) {
        log.info("Adding new book to bookstore");
        final Book newBook = Book.builder()
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
     * Adds new book to bookstore by web page
     *
     * @param book
     * @return book
     */
    @Override
    public Book addNewBookToBookStoreWeb(final Book book) {
        log.info("Adding new book to bookstore");
        final Book newBook = Book.builder()
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
    public void deleteBookFromBookstoreByIsbn(final long isbn) {
        log.info("Deleting book from bookstore by isbn: {}", isbn);
        try {
        bookRepository.deleteById(isbn);
        } catch(BookNotFoundException e) {
        	log.warn(BOOK_NOT_FOUND);
        }
        log.info("Deleted book from bookstore by isbn: {}", isbn);
    }

    /**
     * Updates book in bookstore by web page
     *
     * @param book
     * @return book
     */
    @Override
    public Book updateBookInBookstoreWeb(final Book book) {
        log.info("Updating book: {}", book.toString());
        bookRepository.delete(book);
        bookRepository.save(book);
        log.info("Saving book: {}", book.toString());
        return book;
    }

    /**
     * Updates book in bookstore by Json request
     *
     * @param book
     * @return book
     */
    @Override
    public Book updateBookInBookstoreJson(final Book book) {
        log.info("Updating book: {}", book.toString());
        bookRepository.delete(book);
        bookRepository.save(book);
        log.info("Saving book: {}", book.toString());
        return book;
    }

}
