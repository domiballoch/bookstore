package bookstore.controller;

import bookstore.controller.rest.BasketRestController;
import bookstore.domain.Book;
import bookstore.service.BasketService;
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

import static bookstore.utils.RestControllerTestHelper.getResponseFrom;
import static bookstore.utils.TestDataUtils.BASKET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BasketRestController.class)
public class BasketRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BasketService basketService;

    @SneakyThrows
    @Test
    public void getBasket() {
        when(basketService.getBasket()).thenReturn(BASKET.getBooks());
        final ResultActions resultActions =
                mockMvc.perform(get("/rest/getBasket")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk());

        final List<Book> result = getResponseFrom(resultActions, objectMapper, new TypeReference<>() {});
        assertThat(result).isEqualTo((BASKET.getBooks()));
        verify(basketService, times(1)).getBasket();
    }

    @SneakyThrows
    @Test
    public void removeBookFromBasket() {
        final ResultActions resultActions =
                mockMvc.perform(delete("/rest/removeBookFromBasket/{isbn}", 5)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk());

        verify(basketService, times(1)).removeBookFromBasket(any(Long.class));
    }

}
