package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.domain.Book;
import bookstore.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static bookstore.utils.TestDataUtils.CREATE_ONE_BOOK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AdminServiceImpl adminServiceImpl;

    @DisplayName("Should add new book from bookstore")
    @Test
    public void shouldAddOneNewBookToBookStore(){
        final Book newBook = CREATE_ONE_BOOK;
        when(bookRepository.save(CREATE_ONE_BOOK)).thenReturn(CREATE_ONE_BOOK);
        final Book savedBook = adminServiceImpl.addNewBookToBookStore(
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
        adminServiceImpl.deleteSingleBookFromBookstoreByIsbn(isbn);
        verify(bookRepository, times(1)).deleteById(any(Long.class));
    }
}
