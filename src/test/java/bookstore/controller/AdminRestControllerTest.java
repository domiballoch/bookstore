package bookstore.controller;

import bookstore.controller.rest.AdminRestController;
import bookstore.domain.Book;
import bookstore.service.AdminService;
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

import static bookstore.utils.RestControllerTestHelper.getResponseFrom;
import static bookstore.utils.TestDataUtils.CREATE_ONE_BOOK;
import static bookstore.utils.TestDataUtils.returnOneBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminRestController.class)
public class AdminRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminService adminService;

    @SneakyThrows
    @Test
    public void addNewBookToBookstore() {
        when(adminService.addNewBookToBookstoreJson(any(Book.class))).thenReturn(returnOneBook());
        final ResultActions resultActions =
                mockMvc.perform(post("/rest/addNewBook")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(returnOneBook()))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk());

        final Book result = getResponseFrom(resultActions, objectMapper, new TypeReference<>() {});
        assertThat(result).isEqualTo((returnOneBook()));
        verify(adminService, times(1)).addNewBookToBookstoreJson(any(Book.class));
    }

    @SneakyThrows
    @Test
    public void deleteBookFromBookstore() {
        final ResultActions resultActions =
                mockMvc.perform(delete("/rest/deleteBook/{isbn}", 5)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk());

        verify(adminService, times(1)).deleteBookFromBookstoreByIsbn(any(Long.class));
    }

    @SneakyThrows
    @Test
    public void updateBookInBookstore() {
        when(adminService.updateBookInBookstoreJson(any(Book.class))).thenReturn(returnOneBook());
        final ResultActions resultActions =
                mockMvc.perform(put("/rest/updateBook")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(CREATE_ONE_BOOK))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk());

        final Book result = getResponseFrom(resultActions, objectMapper, new TypeReference<>() {});
        assertThat(result).isEqualTo((returnOneBook()));
        verify(adminService, times(1)).updateBookInBookstoreJson(any(Book.class));
    }
}
