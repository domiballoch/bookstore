package bookstore.controller;

import bookstore.controller.rest.BookRestController;
import bookstore.domain.Category;
import bookstore.exception.BookstoreNotFoundException;
import bookstore.domain.Book;
import bookstore.service.BookService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Collections;
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

    @SneakyThrows
    @Test
    public void findAllBooks() {
        when(bookService.findAllBooks()).thenReturn(BOOKLIST);
        final ResultActions resultActions =
                mockMvc.perform(get("/rest/findAllBooks")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.[]", hasSize(4)));

        final List<Book> result = getResponseFrom(resultActions, objectMapper, new TypeReference<List<Book>>() {});
        assertThat(result).isEqualTo((BOOKLIST));
        verify(bookService, times(1)).findAllBooks();
    }

    @SneakyThrows
    @Test
    public void findBookByIsbn() {
        when(bookService.findBookByIsbn(4)).thenReturn(Optional.of(returnOneBook()));
        final ResultActions resultActions =
                mockMvc.perform(get("/rest/findBook/{isbn}", 4)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(4));

        final Book result = getResponseFrom(resultActions, objectMapper, new TypeReference<Book>() {});
        assertThat(result.getIsbn()).isEqualTo(4);
        assertThat(result).isEqualTo(returnOneBook());
        verify(bookService, times(1)).findBookByIsbn(any(Long.class));
    }

    @SneakyThrows
    @Test
    public void shouldThrow_BookNotFoundException() {
        mockMvc.perform(get("/rest/findBook/{isbn}", 10)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookstoreNotFoundException))
                .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), BOOK_NOT_FOUND));
    }

    @SneakyThrows
    @Test
    public void findBookBySearchTerm() {
        List<Book> resultList = new ArrayList<>();
        resultList.add(returnOneBook());
        when(bookService.findBookBySearchTermIgnoreCase("ti")).thenReturn(resultList);
        final ResultActions resultActions =
        mockMvc.perform(get("/rest/search/{search}", "ti")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].title").value("title4"));

        final List<Book> results = getResponseFrom(resultActions, objectMapper, new TypeReference<List<Book>>() {});
        assertThat(results.get(0).getTitle()).isEqualTo("title4");
        assertThat(results.get(0)).isEqualTo(returnOneBook());
        verify(bookService, times(1)).findBookBySearchTermIgnoreCase(any(String.class));
    }

    @SneakyThrows
    @Test
    public void findBookByCategory() {
        List<Book> resultList = new ArrayList<>();
        resultList.add(returnOneBook());
        when(bookService.findBooksByCategory(Category.SCIENCE_FICTION)).thenReturn(resultList);
        final ResultActions resultActions =
                mockMvc.perform(get("/rest/category/{category}", "SCIENCE_FICTION")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.[0].category").value("SCIENCE_FICTION"));

        final List<Book> results = getResponseFrom(resultActions, objectMapper, new TypeReference<List<Book>>() {});
        assertThat(results.get(0).getCategory()).isEqualTo(Category.SCIENCE_FICTION);
        assertThat(results.get(0)).isEqualTo(returnOneBook());
        verify(bookService, times(1)).findBooksByCategory(any(Category.class));
    }

    @Disabled
    @SneakyThrows
    //@Test
    public void findBookBySearchTermNoContent() {
        when(bookService.findBookBySearchTermIgnoreCase(any(String.class))).thenReturn(Collections.emptyList());
        final ResultActions resultActions =
                mockMvc.perform(get("/rest/search/{search}", "abcdefg")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isNotFound());
        verify(bookService, times(1)).findBookBySearchTermIgnoreCase(any(String.class));
    }

    @Disabled
    @SneakyThrows
    //@Test
    public void findBookByCategoryNoContent() {
        when(bookService.findBooksByCategory(any(Category.class))).thenReturn(Collections.emptyList());
        final ResultActions resultActions =
                mockMvc.perform(get("/rest/category/{category}", "VOID")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isNotFound());
        verify(bookService, times(1)).findBooksByCategory(any(Category.class));
    }

    //TODO:Test getBookstock?
}
