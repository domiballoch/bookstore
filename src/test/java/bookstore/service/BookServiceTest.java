package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.domain.Basket;
import bookstore.domain.Book;
import bookstore.domain.Category;
import bookstore.exception.BookstoreNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookStoreConstants.BOOK_NOT_FOUND;
import static bookstore.utils.TestDataUtils.BOOKLIST;
import static bookstore.utils.TestDataUtils.CREATE_ONE_BOOK;
import static bookstore.utils.TestDataUtils.populateBookList;
import static bookstore.utils.TestDataUtils.returnOneBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Spy
    private Basket basket;

    @Spy
    private BasketService basketService;

    @Spy
    private OrderService orderService;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void prepareData() {
        populateBookList();
    }

    @DisplayName("Should return all books")
    @Test
    public void shouldReturnAllBooks() {
        when(bookRepository.findAll()).thenReturn(BOOKLIST);
        final List<Book> result = bookService.findAllBooks();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(BOOKLIST);
        verify(bookRepository, times(1)).findAll();
    }

    @DisplayName("Should return empty list")
    @Test
    public void shouldReturnEmptyList() {
        final List<Book> emptyList = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(emptyList);
        final List<Book> result = bookService.findAllBooks();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(Collections.EMPTY_LIST);
        verify(bookRepository, times(1)).findAll();
    }

    @DisplayName("Should return one book by isbn")
    @Test
    public void shouldReturnOneBookByIsbn(){
        final long isbn = 4;
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(returnOneBook()));
        final Optional<Book> result = bookService.findBookByIsbn(isbn);

        assertThat(result).isEqualTo(Optional.of(returnOneBook()));
        verify(bookRepository, times(1)).findById(any(Long.class));
    }

    @Disabled //Not needed as using controller advice
    @DisplayName("Should throw bookstore not found exception when return one book by isbn")
    @Test
    public void shouldThrowBookstoreNotFoundException_WhenReturnOneBookByIsbn(){
        final long isbn = 99999;
        Exception exception = assertThrows(BookstoreNotFoundException.class, () -> {
            bookService.findBookByIsbn(isbn);
        });
        final String expectedMessage = BOOK_NOT_FOUND;
        final String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("Should return book by search term")
    @Test
    public void shouldReturnBookBySearchTerm(){
        List<Book> resultList = new ArrayList<>();
        resultList.add(returnOneBook());
        when(bookRepository.findBookBySearchTermIgnoreCase(any(String.class))).thenReturn(resultList);
        final List<Book> results = bookService.findBookBySearchTermIgnoreCase("ti");

        assertThat(results).isNotNull();
        assertThat(results.get(0).getTitle()).isEqualTo("title4");
        assertThat(results.get(0)).isEqualTo(returnOneBook());
        verify(bookRepository, times(1)).findBookBySearchTermIgnoreCase(any(String.class));
    }

    @DisplayName("Should return book by category")
    @Test
    public void shouldReturnBookByCategory(){
        List<Book> resultList = new ArrayList<>();
        resultList.add(returnOneBook());
        when(bookRepository.findByCategory(any(Category.class))).thenReturn(resultList);
        final List<Book> results = bookService.findBooksByCategory(Category.SCIENCE_FICTION);

        assertThat(results).isNotNull();
        assertThat(results.get(0).getCategory()).isEqualTo(Category.SCIENCE_FICTION);
        assertThat(results.get(0)).isEqualTo(returnOneBook());
        verify(bookRepository, times(1)).findByCategory(any(Category.class));
    }

    @DisplayName("Should return book stock")
    @Test
    public void shouldReturnBookStock() {
        when(bookRepository.getBookStock(any(Long.class))).thenReturn(10);
        final int result = bookService.getBookStock(1L);

        assertThat(result).isEqualTo(10);
        verify(bookRepository, times(1)).getBookStock(any(Long.class));
    }

    @DisplayName("Should return book is in stock")
    @Test
    public void shouldReturnTrueIfBookIsInStock() {
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(returnOneBook()));
        final boolean result = bookService.inStock(4L);

        assertThat(result).isEqualTo(true);
        verify(bookRepository, times(1)).findById(any(Long.class));
    }

    @DisplayName("Should return book out of stock")
    @Test
    public void shouldReturnFalseIfBookIsOutOfStock() {
        final Book book = returnOneBook();
        book.setStock(0);
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        final boolean result = bookService.inStock(4L);

        assertThat(result).isEqualTo(false);
        verify(bookRepository, times(1)).findById(any(Long.class));
    }

    @DisplayName("Should add book to basket and reduce stock")
    @Test
    public void shouldAddBookToBasketAndReduceBookStock() {
        final Book book = CREATE_ONE_BOOK;
        //stock is 10
        book.setStock(10);

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(basketService.calculateBasket(any(List.class))).thenReturn(BigDecimal.valueOf(49.99));
        bookService.addBookToBasket(5);

        assertThat(basket.getBooks().size()).isEqualTo(1);
        assertThat(book.getStock()).isEqualTo(9);
    }

    @DisplayName("Should update stock")
    @Test
    public void shouldUpdateStock() {
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(CREATE_ONE_BOOK));
        assertThat(CREATE_ONE_BOOK.getStock()).isEqualTo(10);
        bookService.addBookToBasket(5);

        assertThat(basket.getBooks().get(0).getStock()).isEqualTo(9);
        assertThat(basket.getBooks().size()).isEqualTo(1);

        doNothing().when(bookRepository).updateBookStock(any(Integer.class));
        bookService.updateBookStock(basket.getBooks());

        when(bookRepository.getBookStock(any(Long.class))).thenReturn(9);
        assertThat(bookService.getBookStock(5)).isEqualTo(9);
        verify(bookRepository, times(1)).updateBookStock(any(Integer.class));
    }

    //unhappy paths
    //TODO:@DisplayName("Should not allow empty or null or min or max)
}
