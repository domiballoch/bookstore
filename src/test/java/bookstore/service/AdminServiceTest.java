package bookstore.service;

import bookstore.dao.BookRepository;
import bookstore.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static bookstore.utils.TestDataUtils.CREATE_ANOTHER_BOOK;
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
        when(bookRepository.save(any(Book.class))).thenReturn(newBook);
        final Book savedBook = adminServiceImpl.addNewBookToBookstoreJson(newBook);

        savedBook.setIsbn(5L); //genius
        assertThat(savedBook).isEqualTo(newBook);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("Should delete one book from bookstore")
    @Test
    public void shouldDeleteOneBookFromBookStore(){
        final long isbn = 4;
        adminServiceImpl.deleteBookFromBookstoreByIsbn(isbn);
        verify(bookRepository, times(1)).deleteById(any(Long.class));
    }

    @DisplayName("Should update one book from bookstore")
    @Test
    public void shouldUpdateOneBookFromBookStore(){
        Book book = (CREATE_ONE_BOOK);
        final Book newBookDetails = (CREATE_ANOTHER_BOOK);

        when(bookRepository.findById(5L)).thenReturn(Optional.of(book));
        when(adminServiceImpl.updateBookInBookstoreJson(newBookDetails, 5)).thenReturn(newBookDetails);
        newBookDetails.setIsbn(CREATE_ONE_BOOK.getIsbn());

        bookRepository.save(book);
        final Book updatedBook = adminServiceImpl.updateBookInBookstoreJson(newBookDetails, 5);
        assertThat(updatedBook).isEqualTo(newBookDetails);
        verify(bookRepository, times(2)).save(any(Book.class));
    }

    //add web service tests for completeness
}
