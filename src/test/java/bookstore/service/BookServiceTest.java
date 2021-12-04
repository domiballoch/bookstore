package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.domain.Book;
import bookstore.domain.Category;
import bookstore.exception.BookDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookConstants.DATABASE_NOT_AVAILABLE;
import static bookstore.utils.TestDataUtils.BOOKLIST;
import static bookstore.utils.TestDataUtils.returnBookList;
import static bookstore.utils.TestDataUtils.returnOneBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void prepareData() {
        returnBookList();
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
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(returnOneBook()));
        final Optional<Book> result = bookService.findBookByIsbn(isbn);

        assertThat(result).isEqualTo(Optional.ofNullable(returnOneBook()));
        verify(bookRepository, times(1)).findById(any(Long.class));
    }

    @DisplayName("Should throw book not found exception when return one book by isbn")
    @Test
    public void shouldThrowBookNotFoundException_WhenReturnOneBookByIsbn(){
        final long isbn = 10;
        Exception exception = assertThrows(BookDataException.class, () -> {
            bookService.findBookByIsbn(isbn);
        });
        final String expectedMessage = DATABASE_NOT_AVAILABLE;
        final String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("Should return book in stock")
    @Test
    public void shouldReturnBookInStock(){
        final String title = "title3";
        final String author = "author3";
        when(bookRepository.findByTitleAndAuthor(any(String.class), any(String.class))).thenReturn(BOOKLIST);
        final boolean result = bookService.inStock("title3", "author3");

        assertThat(result).isEqualTo(true);
        verify(bookRepository, times(1)).findByTitleAndAuthor(any(String.class),
                any(String.class));
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
        when(bookRepository.findBooksByCategory(any(Category.class))).thenReturn(resultList);
        final List<Book> results = bookService.findBooksByCategory(Category.SCIENCE_FICTION);

        assertThat(results).isNotNull();
        assertThat(results.get(0).getCategory()).isEqualTo(Category.SCIENCE_FICTION);
        assertThat(results.get(0)).isEqualTo(returnOneBook());
        verify(bookRepository, times(1)).findBooksByCategory(any(Category.class));
    }

    @DisplayName("Should return book stock")
    @Test
    public void shouldReturnBookStock() {
        when(bookRepository.getBookStock(any(String.class), any(String.class))).thenReturn(1);
        final int result = bookService.getBookStock("Title", "Author");

        assertThat(result).isEqualTo(1);
        verify(bookRepository, times(1)).getBookStock(any(String.class), any(String.class));
    }

    @DisplayName("Should return book is in stock")
    @Test
    public void shouldReturnTrueIfBookIsInStock() {
        when(bookRepository.findByTitleAndAuthor(any(String.class), any(String.class))).thenReturn(List.of(returnOneBook()));
        final boolean result = bookService.inStock("Title", "Author");

        assertThat(result).isEqualTo(true);
        verify(bookRepository, times(1)).findByTitleAndAuthor(any(String.class), any(String.class));
    }

    @DisplayName("Should return book out of stock")
    @Test
    public void shouldReturnFalseIfBookIsOutOfStock() {
        when(bookRepository.findByTitleAndAuthor(any(String.class), any(String.class))).thenReturn(Collections.EMPTY_LIST);
        final boolean result = bookService.inStock("Title", "Author");

        assertThat(result).isEqualTo(false);
        verify(bookRepository, times(1)).findByTitleAndAuthor(any(String.class), any(String.class));
    }

    //@DisplayName("Should increase stock when add one book")
    //@DisplayName("Should decrease stock when delete one book")
    //@DisplayName("Should return false when book is out of stock")
    //@DisplayName("Should add book to basket")
    //@DisplayName("Should remove book from basket")

    //unhappy paths
    //@DisplayName("Should not allow empty or null or min or max - separate tests")
}
