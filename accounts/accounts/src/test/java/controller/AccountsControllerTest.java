package controller;

import com.eazybytes.accounts.controller.AccountsController;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.service.impl.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    public void shouldUpdateTheAccount() throws Exception {
        CustomerDto customerDto = new CustomerDto();

        when(accountService.updateAccount(customerDto)).thenReturn(true);

        mockMvc.perform(put("/api/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto))
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.statusMsg").value("Request processed successfully")
        );
    }

    @Test
    public void shouldThrowErrorWhenAccountNotUpdated() throws Exception{

        CustomerDto customerDto = new CustomerDto();

        when(accountService.updateAccount(any())).thenReturn(false);

        mockMvc.perform(put("/api/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto))
        ).andExpectAll(
                status().isExpectationFailed(),
                jsonPath("$.statusMsg").value("Update operation failed. Please try again or contact Dev team")
        );

    }

    @Test
    public void shouldDeleteAccountSuccessfully() throws Exception {
        CustomerDto customerDto = new CustomerDto();

        when(accountService.deleteAccount(anyString())).thenReturn(true);

        mockMvc.perform(delete("/api/delete")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("mobileNumber",anyString())
                        .content(objectMapper.writeValueAsString(customerDto))
                ).andExpectAll(
                status().isOk(),
                jsonPath("$.statusMsg").value("Request processed successfully")
        );
    }

    @Test
    public void shouldThrowErrorWhenUnsuccessful() throws Exception{
        CustomerDto customerDto = new CustomerDto();

        when(accountService.deleteAccount(anyString())).thenReturn(false);

        mockMvc.perform(delete("/api/delete")
                .param("mobileNumber", anyString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isExpectationFailed(),
                jsonPath("$.statusMsg").value("Delete operation failed. Please try again or contact Dev team")
        );
    }






}