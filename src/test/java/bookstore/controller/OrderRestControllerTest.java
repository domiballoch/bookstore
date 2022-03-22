package bookstore.controller;

import bookstore.controller.rest.OrderRestController;
import bookstore.domain.OrderDetails;
import bookstore.domain.Users;
import bookstore.exception.BookstoreNotFoundException;
import bookstore.service.OrderService;
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

import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookStoreConstants.ORDER_NOT_FOUND;
import static bookstore.utils.RestControllerTestHelper.getResponseFrom;
import static bookstore.utils.TestDataUtils.CREATE_ONE_ORDER;
import static bookstore.utils.TestDataUtils.ORDERLIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderRestController.class)
public class OrderRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @SneakyThrows
    @Test
    public void findAllOrders() {
        when(orderService.findAllOrders()).thenReturn(ORDERLIST);
        final ResultActions resultActions =
                mockMvc.perform(get("/rest/findAllOrders")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk());

        final List<OrderDetails> result = getResponseFrom(resultActions, objectMapper, new TypeReference<List<OrderDetails>>() {});
        assertThat(result).isEqualTo((ORDERLIST));
        verify(orderService, times(1)).findAllOrders();
    }

    @SneakyThrows
    @Test
    public void findOrderById() {
        when(orderService.findOrderById(1)).thenReturn(Optional.of(CREATE_ONE_ORDER));
        final ResultActions resultActions =
                mockMvc.perform(get("/rest/findOrder/{isbn}", 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.orderDetailsId").value(1));

        final OrderDetails result = getResponseFrom(resultActions, objectMapper, new TypeReference<OrderDetails>() {});
        assertThat(result.getOrderDetailsId()).isEqualTo(1);
        assertThat(result).isEqualTo(CREATE_ONE_ORDER);
        verify(orderService, times(1)).findOrderById(any(Long.class));
    }

    @Disabled
    @SneakyThrows
    @Test
    public void shouldThrow_BookStoreNotFoundException() {
        mockMvc.perform(get("/rest/findOrder/{orderDetailsId}", 99999)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookstoreNotFoundException))
                .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), ORDER_NOT_FOUND));
    }

    @SneakyThrows
    @Test
    public void submitOrder() {
        when(orderService.submitOrder(any(Users.class))).thenReturn(CREATE_ONE_ORDER);
        final ResultActions resultActions =
                mockMvc.perform(post("/rest/submitOrder")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(CREATE_ONE_ORDER))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk());

        final OrderDetails result = getResponseFrom(resultActions, objectMapper, new TypeReference<>() {});
        assertThat(result).isEqualTo((CREATE_ONE_ORDER));
        verify(orderService, times(1)).submitOrder(any(Users.class));

    }
}
