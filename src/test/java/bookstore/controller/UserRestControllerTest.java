package bookstore.controller;

import bookstore.controller.rest.UserRestController;
import bookstore.domain.Users;
import bookstore.exception.BookstoreNotFoundException;
import bookstore.service.UserService;
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

import static bookstore.utils.BookConstants.USER_NOT_FOUND;
import static bookstore.utils.RestControllerTestHelper.getResponseFrom;
import static bookstore.utils.TestDataUtils.CREATE_ONE_USER;
import static bookstore.utils.TestDataUtils.USERLIST;
import static bookstore.utils.TestDataUtils.returnOneBook;
import static bookstore.utils.TestDataUtils.returnOneUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @SneakyThrows
    @Test
    public void findAllUsers() {
        when(userService.findAllUsers()).thenReturn(USERLIST);
        final ResultActions resultActions =
                mockMvc.perform(get("/rest/findAllUsers")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk());
        //.andExpect(jsonPath("$.[]", hasSize(4)));

        final List<Users> result = getResponseFrom(resultActions, objectMapper, new TypeReference<>() {});
        assertThat(result).isEqualTo((USERLIST));
        verify(userService, times(1)).findAllUsers();
    }

    @SneakyThrows
    @Test
    public void findUserById() {
        when(userService.findUserById(4)).thenReturn(Optional.ofNullable(returnOneUser()));
        final ResultActions resultActions =
                mockMvc.perform(get("/rest/findUser/{userId}", 4)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.userId").value(4));

        final Users result = getResponseFrom(resultActions, objectMapper, new TypeReference<>() {});
        assertThat(result.getUserId()).isEqualTo(4);
        assertThat(result).isEqualTo(returnOneUser());
        verify(userService, times(1)).findUserById(any(Long.class));
    }

    @SneakyThrows
    @Test
    public void addNewUser() {
        when(userService.addNewUser(any(Users.class))).thenReturn(returnOneUser());
        final ResultActions resultActions =
                mockMvc.perform(post("/rest/addNewUser")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(returnOneBook()))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk());

        final Users result = getResponseFrom(resultActions, objectMapper, new TypeReference<>() {});
        assertThat(result).isEqualTo((returnOneUser()));
        verify(userService, times(1)).addNewUser(any(Users.class));
    }

    @SneakyThrows
    @Test
    public void deleteUser() {
        final ResultActions resultActions =
                mockMvc.perform(delete("/rest/deleteUser/{userId}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(any(Long.class));
    }

    @SneakyThrows
    @Test
    public void updateUser() {
        when(userService.updateUser(any(Users.class), any(Long.class))).thenReturn(returnOneUser());
        final ResultActions resultActions =
                mockMvc.perform(put("/rest/updateUser/{userId}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(CREATE_ONE_USER))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk());

        final Users result = getResponseFrom(resultActions, objectMapper, new TypeReference<>() {});
        assertThat(result).isEqualTo((returnOneUser()));
        verify(userService, times(1)).updateUser(any(Users.class), any(Long.class));
    }

    @SneakyThrows
    @Test
    public void shouldThrow_BookstoreNotFoundException() {
        mockMvc.perform(get("/rest/findUser/{userId}", 10)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookstoreNotFoundException))
                .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), USER_NOT_FOUND));
    }
}
