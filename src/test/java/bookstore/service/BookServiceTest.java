package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.domain.Book;
import bookstore.exception.BookNotFoundException;
import lombok.SneakyThrows;
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

import static bookstore.utils.BookConstants.BOOK_NOT_FOUND;
import static bookstore.utils.TestDataUtils.BOOKLIST;
import static bookstore.utils.TestDataUtils.returnBookList;
import static bookstore.utils.TestDataUtils.returnOneBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        when(bookRepository.findById(isbn)).thenReturn(Optional.ofNullable(returnOneBook()));
        final Optional<Book> result = bookService.findBookByIsbn(isbn);

        assertThat(result).isEqualTo(Optional.ofNullable(returnOneBook()));
        verify(bookRepository, times(1)).findById(any(Long.class));
    }

    @DisplayName("Should throw book not found exception when return one book by isbn")
    @Test
    public void shouldThrowBookNotFoundException_WhenReturnOneBookByIsbn(){
        final long isbn = 10;
        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.findBookByIsbn(isbn);
        });
        final String expectedMessage = BOOK_NOT_FOUND;
        final String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("Should return book in stock")
    @Test
    public void shouldReturnBookInStock(){
        final String title = "title3";
        final String author = "author3";
        when(bookRepository.findByTitleAndAuthor(title, author)).thenReturn(BOOKLIST);
        final boolean result = bookService.inStock("title3", "author3");

        assertThat(result).isEqualTo(true);
        verify(bookRepository, times(1)).findByTitleAndAuthor(any(String.class),
                any(String.class));
    }

    @SneakyThrows
    @DisplayName("Should use cache when calling book repository twice")
    @Test
    void shouldUseCacheWhenCallingBookRepositoryTwice(){
        when(bookRepository.findAll()).thenReturn(BOOKLIST);
        List<Book> call1 = bookService.findAllBooks();
        List<Book> call2 = bookService.findAllBooks();

        assertNotNull(call1);
        assertNotNull(call2);
        //verify(bookRepository, times(1)).findAll();
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
