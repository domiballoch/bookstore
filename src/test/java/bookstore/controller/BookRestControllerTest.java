package bookstore.controller;

import bookstore.exception.BookNotFoundException;
import bookstore.domain.Book;
import bookstore.service.AdminService;
import bookstore.service.BookService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static bookstore.utils.TestDataUtils.BOOKLIST;
import static bookstore.utils.TestDataUtils.returnOneBook;
import static bookstore.utils.BookConstants.BOOK_NOT_FOUND;
import static bookstore.utils.RestControllerTestHelper.getResponseFrom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private AdminService adminService;

    @SneakyThrows
    @Test
    public void findAllBooks() {
        when(bookService.findAllBooks()).thenReturn(BOOKLIST);
        final ResultActions resultActions =
                mockMvc.perform(get("/findAllBooks/rest")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.[*]", hasSize(4)));

        final List<Book> result = getResponseFrom(resultActions, objectMapper, new TypeReference<List<Book>>() {});
        assertThat(result).isEqualTo((BOOKLIST));
        verify(bookService, times(1)).findAllBooks();
    }

    @SneakyThrows
    @Test
    public void findBookByIsbn() {
        when(bookService.findBookByIsbn(4)).thenReturn(Optional.ofNullable(returnOneBook()));
        final ResultActions resultActions =
                mockMvc.perform(get("/findBook/rest/{isbn}", 4)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(4));

        final Book result = getResponseFrom(resultActions, objectMapper, new TypeReference<Book>() {});
        assertThat(result).isEqualTo(returnOneBook());
        verify(bookService, times(1)).findBookByIsbn(any(Long.class));
    }

    @SneakyThrows
    @Test
    public void shouldThrow_BookNotFoundException() {
        mockMvc.perform(get("/findBook/rest/{isbn}", 10)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookNotFoundException))
                .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), BOOK_NOT_FOUND));
    }
}
