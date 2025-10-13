package controller;

import com.eazybytes.accounts.controller.AccountsController;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.service.impl.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountsController.class)
@ContextConfiguration(classes = AccountsController.class)
class AccountsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    AccountServiceImpl accountService;

    @Test
    public void shouldCreateAccount() throws Exception {

        CustomerDto customerDto = new CustomerDto();

        mockMvc.perform(post("/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto))
        ).andExpectAll(
                status().isCreated(),
                jsonPath("$.statusCode").value("201"),
                jsonPath("$.statusMsg").value("Account created successfully")
        );
    }

    @Test
    public void shouldCreateAccount1() throws Exception {
        CustomerDto customerDto = new CustomerDto();

        doNothing().when(accountService).createAccount(any(CustomerDto.class));

        mockMvc.perform(post("/api/create")
                        .content(objectMapper.writeValueAsString(customerDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.statusCode").value("201"),
                        jsonPath("$.statusMsg").value("Account created successfully")
                );
    }

    @Test
    public void shouldGetAccount() throws Exception {
        CustomerDto customerDto = new CustomerDto();

        when(accountService.fetchAccount(anyString())).thenReturn(customerDto);

        mockMvc.perform(get("/api/fetch")
                .contentType(MediaType.APPLICATION_JSON)
                .param("mobileNumber", "1234567890")
        ).andExpectAll(
                status().isOk()
        );

    }


}