package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.exception.BookNotFoundException;
import bookstore.domain.Book;
import bookstore.domain.Category;
import bookstore.utils.BookConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookConstants.BOOK_NOT_FOUND;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    //user-----

    /**
     * Finds all books from Bookstore DB(Book table)
     * If empty then should return Collections.EMPTY_LIST by default
     *
     * @return - List<Book>
     */
    //@Transactional(readOnly = true)
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
    @SneakyThrows
    @Override
    public Optional<Book> findBookByIsbn(final long isbn){
        log.info("Finding book by isbn: {}", isbn);
        //return bookRepository.findById(isbn);
        return Optional.ofNullable(bookRepository.findById(isbn)
                //Should not be handling controller level exceptions here
                //Should be handling DB exceptions in service layer
                .orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND)));
    }

    /**
     * Counts results from Bookstore DB(Book table)
     *
     * @param - title
     * @param - author
     * @return - Returns Stock value as long
     */
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
    @Override
    public boolean inStock(final String title, final String author) { //TODO: Make this cacheable?
        final List<Book> bookList = bookRepository.findByTitleAndAuthor(title, author);
        final int inStock = bookList.size();
        return inStock > 0;
    }

    @Override
    public List<Book> findBooksByCategory(final Category category) {
       return bookRepository.findBooksByCategory(category);
    }

    @Override
    public List<Book> findBookBySearchTerm(final String search) {
        return null; //bookRepository.findBookBySearchTerm);
    }

    //admin-----

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
                                      final String author, final BigDecimal price) {
        log.info("Adding new book to bookstore");
        final Book newBook = Book.builder()
                .isbn(isbn)
                .category(category)
                .title(title)
                .author(author)
                .price(price)
                .build();
        bookRepository.save(newBook);

        //TODO: Need to increase stock of book per isbn
        log.info("New book added to bookstore: {}", newBook.toString());
        return newBook;
    }

//    @Override
//    public Book amendBook(final long isbn, final Category category, final String title,
//                          final String author, final BigDecimal price) {
//        log.info("Amending book: {}", isbn);
//        final Optional<Book> book = bookRepository.findById(isbn);
//        if(book.isPresent()) {
//
//            bookRepository.save(book);
//        }
//    }

    /**
     * Deletes single Book from Bookstore DB(Book table)
     * Decreases Stock of Book if already exists
     *
     * @param isbn
     */
    @Override
    public void deleteSingleBookFromBookstoreByIsbn(final long isbn) {
        log.info("Deleting book from bookstore by isbn: {}", isbn);
        bookRepository.deleteById(isbn); //TODO: Need to add exception logic for things like this
        log.info("Deleted book from bookstore by isbn: {}", isbn);
    }

//    @Override
//        public void deleteBookByTypeFromBookStoreByIsbn(final long isbn) {
//        log.info(BookConstants.DELETING_GROUP_FROM_BOOKSTORE, isbn);
//        bookRepository.deleteById(isbn); //TODO: Need groupId created in book OR add quantity as another DB
//            log.info(BookConstants.DELETED_GROUP_FROM_BOOKSTORE, isbn);
//        }
}
