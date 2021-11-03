package bookstore.controller;

import bookstore.controller.rest.AdminRestController;
import bookstore.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@WebMvcTest(AdminRestController.class)
public class AdminRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminService adminService;

    @Test
    public void addNewBookToBookstore() {

    }

    @Test
    public void addDeleteBookFromBookstore() {

    }

    @Test
    public void updateBookInBookstore() {

    }
}
