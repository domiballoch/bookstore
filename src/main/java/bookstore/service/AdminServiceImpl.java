package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.domain.Book;
import bookstore.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
//    public Book amendBook(final long isbn, final Category category, final String title,
//                          final String author, final BigDecimal price) {
//        log.info("Amending book: {}", isbn);
//        final Optional<Book> book = bookRepository.findById(isbn);
//        if(book.isPresent()) {
//
//            bookRepository.save(book);
//        }
//    }

}
