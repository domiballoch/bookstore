package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.domain.Basket;
import bookstore.domain.Book;
import bookstore.domain.Category;
import bookstore.exception.BookDataException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookConstants.DATABASE_NOT_AVAILABLE;

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
    private Basket basket;

    /**
     * Finds all books from Bookstore DB(Book table)
     * If empty then should return Collections.EMPTY_LIST by default
     *
     * @return - List<Book>
     */
    //@Transactional(readOnly = true)
    @Cacheable("books")
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
    @SneakyThrows
    @Override
    public Optional<Book> findBookByIsbn(final long isbn){
        log.info("Finding book by isbn: {}", isbn);
        //return bookRepository.findById(isbn);
        return Optional.ofNullable(bookRepository.findById(isbn)
                .orElseThrow(() -> new BookDataException(DATABASE_NOT_AVAILABLE)));
    }

    /**
     * Counts results from Bookstore DB(Book table)
     *
     * @param - title
     * @param - author
     * @return - Returns Stock value as long
     */
    @Cacheable("bookstock")
    @Override
    public int getBookStock(final String title, final String author) {
        log.info("Getting book stock by isbn: {}, {}", title, author);
        final int bookStock = bookRepository.getBookStock(title, author);
        log.info("Current stock of item: {}", bookStock);
        return bookStock;
    }

    /**
     * Stores results from Bookstore DB(Book table) as a List
     *
     * @param - title
     * @param - author
     * @return - Returns a boolean if Book exists or not
     */
    @Cacheable("instock)")
    @Override
    public boolean inStock(final String title, final String author) {
        final List<Book> bookList = bookRepository.findByTitleAndAuthor(title, author);
        final int inStock = bookList.size();
        return inStock > 0;
    }

    /**
     * Finds Books by Category
     * //TODO: complete sql call
     *
     * @param category
     * @return List<Book>
     */
    @Cacheable("book")
    @Override
    public List<Book> findBooksByCategory(final Category category) {
        log.info("Finding book by category: {}", category);
       return bookRepository.findBooksByCategory(category);
    }

    /**
     * Finds Books by search term using fuzzy search logic
     * //TODO: add fuzzy search logic
     *
     * @param search
     * @return List<Book>
     */
    @Override
    public List<Book> findBookBySearchTerm(final String search) {
        log.info("Finding book by search term: {}", search);
        //fuzzy search
        return null; //bookRepository.findBookBySearchTerm);
    }

    /**
     * Adds Book to Basket(List)
     *
     * @param book
     */
    @Override
    public void addBookToBasket(final Book book) {
        log.info("Adding book by to basket: {}", book);
        //add book to a basket list and reduce stock by quantity
        //cache evict
        basket.add(book);
    }

    /**
     * Removes Book from Basket(List)
     * First adds removed Book(s) to separate list the compares against Basket
     * If Basket contains any then they get removed - avoiding ConcurrentModificationException of Stream
     *
     * @param book
     */
    @Override
    public void removeBookFromBasket(final Book book) {
        log.info("Removing book from basket: {}", book);
        //remove book from basket list and increase stock by quantity
        List<Object> removedBooks = new ArrayList<>();

        basket.stream().forEach(b -> {
            b.equals(book);
            removedBooks.add(b);
        });

        if(CollectionUtils.containsAny(removedBooks, basket)) {
            basket.removeAll(removedBooks);
        }
    }
}
