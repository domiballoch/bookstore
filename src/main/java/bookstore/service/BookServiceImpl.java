package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.domain.Basket;
import bookstore.domain.Book;
import bookstore.domain.Category;
import bookstore.exception.BookstoreDataException;
import bookstore.exception.BookstoreNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookStoreConstants.DATABASE_NOT_AVAILABLE;
import static bookstore.utils.BookStoreConstants.OUT_OF_STOCK;

/**
 * Using Cachable to speed up processing
 * When book is added to basket - use cacheEvict(value="book", key="#book.isbn")
 * When book is updated - use cachePut(value="book", key="#book.isbn")
 * Add Cache test with timer
 */
@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BasketService basketService;

    @Autowired
    private Basket basket;

    /**
     * Finds all books from Bookstore DB(Book table)
     * If empty then should return Collections.EMPTY_LIST by default
     *
     * @return - List<Book>
     */
    @Override
    public List<Book> findAllBooks() {
        log.info("Finding all books");
        return bookRepository.findAll();
    }

    /**
     * Finds Book from Bookstore DB(Book table) by isbn
     *
     * @param - isbn
     * @return - Returns Optional<Book> by isbn else throw BookNotFoundException
     */
    @Cacheable("book")
    @Override
    public Optional<Book> findBookByIsbn(final long isbn){
        log.info("Finding book by isbn: {}", isbn);
        return Optional.ofNullable(bookRepository.findById(isbn)
                .orElseThrow(() -> new BookstoreDataException(DATABASE_NOT_AVAILABLE)));
    }
    
    @Override
    public Book findBookByIsbnWeb(final long isbn){
        log.info("Finding book by isbn: {}", isbn);
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new BookstoreDataException(DATABASE_NOT_AVAILABLE));
    }

    /**
     * Counts results from Bookstore DB(Book table)
     *
     * @param - isbn
     * @return - Returns Stock value as long
     */
    @Cacheable("bookstock")
    @Override
    public int getBookStock(final long isbn) {
        log.info("Getting book stock by isbn: {}", isbn);
        final int bookStock = bookRepository.getBookStock(isbn);
        log.info("Current stock of item: {}", bookStock);
        return bookStock;
    }

    /**
     * Stores results from Bookstore DB(Book table) as a List
     *
     * @param - isbn
     * @return - Returns a boolean if Book exists or not
     */
    @Cacheable("instock)")
    @Override
    public boolean inStock(final long isbn) {
        final Optional<Book> book = bookRepository.findById(isbn);
        final int inStock = book.get().getStock();
        return inStock > 0;
    }

    /**
     * Finds Books by Category
     *
     * @param category
     * @return - List<Book>
     */
    @Cacheable("book")
    @Override
    public List<Book> findBooksByCategory(final Category category) {
        log.info("Finding book by category: {}", category);
       return bookRepository.findByCategory(category);
    }

    /**
     * Finds Books by search term using fuzzy search logic
     *
     * @param search
     * @return - List<Book>
     */
    @Override
    public List<Book> findBookBySearchTermIgnoreCase(final String search) {
        log.info("Finding book by search term: {}", search);
        //fuzzy search by first three chars
        return bookRepository.findBookBySearchTermIgnoreCase(search);
    }

    /**
     * Adds Book to Basket(List) if in stock
     * If not in stock, throw not found exception
     * //cache evict
     *
     * @param book
     */
    @Override
    public Basket addBookToBasket(final Book book) {
        if(inStock(book.getIsbn()) == true) {
            basket.addBook(book);
            log.info("Book added to basket: {}", book);
            book.setStock(book.getStock() - 1);
            log.info("Total price is {} ", basketService.calculateBasket(basket));
        } else {
            log.info("Book is out of stock {}", book);
            throw new BookstoreNotFoundException(OUT_OF_STOCK);
        }
        return basket;
    }

    /**
     * Updates book stock in DB for each book after order is saved
     *
     * @param books
     */
    @Override
    public void updateBookStock(final List<Book> books) {
        log.info("Updating book stock");
        books.forEach(book -> {
            bookRepository.updateBookStock(book.getStock());
        });
    }

}
