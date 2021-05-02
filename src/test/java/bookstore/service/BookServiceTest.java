package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.exception.BookNotFoundException;
import bookstore.domain.Book;
import bookstore.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static bookstore.utils.TestDataUtils.BOOKLIST;
import static bookstore.utils.TestDataUtils.CREATE_ONE_BOOK;
import static bookstore.utils.TestDataUtils.returnBookList;
import static bookstore.utils.TestDataUtils.returnOneBook;
import static bookstore.utils.BookConstants.BOOK_NOT_FOUND;
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
    private BookServiceImpl bookServiceImpl;

    @BeforeEach
    public void prepareData() {
        returnBookList();
    }

    @DisplayName("Should return all books")
    @Test
    public void shouldReturnAllBooks() {
        when(bookRepository.findAll()).thenReturn(BOOKLIST);
        final List<Book> result = bookServiceImpl.findAllBooks();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(BOOKLIST);
        verify(bookRepository, times(1)).findAll();
    }

    @DisplayName("Should return empty list")
    @Test
    public void shouldReturnEmptyList() {
        final List<Book> emptyList = new ArrayList<>();
        when(bookRepository.findAll()).thenReturn(emptyList);
        final List<Book> result = bookServiceImpl.findAllBooks();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(Collections.EMPTY_LIST);
        verify(bookRepository, times(1)).findAll();
    }

    @DisplayName("Should return one book by isbn")
    @Test
    public void shouldReturnOneBookByIsbn(){
        final long isbn = 4;
        when(bookRepository.findById(isbn)).thenReturn(Optional.ofNullable(returnOneBook()));
        final Optional<Book> result = bookServiceImpl.findBookByIsbn(isbn);

        assertThat(result).isEqualTo(Optional.ofNullable(returnOneBook()));
        verify(bookRepository, times(1)).findById(any(Long.class));
    }

    @DisplayName("Should throw book not found exception when return one book by isbn")
    @Test
    public void shouldThrowBookNotFoundException_WhenReturnOneBookByIsbn(){
        final long isbn = 10;
        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookServiceImpl.findBookByIsbn(isbn);
        });
        final String expectedMessage = BOOK_NOT_FOUND;
        final String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("Should add new book from bookstore")
    @Test
    public void shouldAddOneNewBookToBookStore(){
        final Book newBook = CREATE_ONE_BOOK;
        when(bookRepository.save(newBook)).thenReturn(newBook);
        final Book savedBook = bookServiceImpl.addNewBookToBookStore(
                5,
                Category.COOKING,
                "title5",
                "author5",
                BigDecimal.valueOf(49.99));

        assertThat(savedBook).isEqualTo(newBook);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("Should delete one book from bookstore")
    @Test
    public void shouldDeleteOneBookFromBookStore(){
        final long isbn = 4;
        //doNothing().when(bookRepository.deleteById(isbn));
        bookServiceImpl.deleteSingleBookFromBookstoreByIsbn(isbn);
        verify(bookRepository, times(1)).deleteById(any(Long.class));
    }

    @DisplayName("Should return book in stock")
    @Test
    public void shouldReturnBookInStock(){
        final String title = "title3";
        final String author = "author3";
        when(bookRepository.findByTitleAndAuthor(title, author)).thenReturn(BOOKLIST);
        final boolean result = bookServiceImpl.inStock("title3", "author3");

        assertThat(result).isEqualTo(true);
        verify(bookRepository, times(1)).findByTitleAndAuthor(any(String.class), any(String.class));
    }

    //@DisplayName("Should increase stock when add one book")
    //@Test
    //public void shouldIncreaseStockWhenAddOneBook(){}

    //@DisplayName("Should decrease stock when delete one book")
    //@DisplayName("Should return true when book is in stock")
    //@DisplayName("Should return false when book is out of stock")
    //@DisplayName("Should return books by category")
    //@DisplayName("Should return books by search term")
}
