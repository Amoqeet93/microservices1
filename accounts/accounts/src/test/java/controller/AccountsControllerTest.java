package controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AccountsController.class)
@ContextConfiguration(classes = AccountsController.class)
class AccountsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getHelloTest() throws Exception {

        mockMvc.perform(get("/api/hello")
                        .param("name","Moqeet"))
                .andExpectAll(
                content().string("Hello Moqeet"));

    }

}