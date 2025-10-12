package controller;

import com.eazybytes.accounts.controller.AccountsController;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.model.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        CustomerDto customerDto = new CustomerDto("Moqeet", "moqeet@example.com", "01212");

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
    public void shouldCreateAccount1() throws Exception{
        CustomerDto customerDto = new CustomerDto("Moqeet", "moqeet@example.com", "01212");

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

//    @Test
//    public void shouldReturnListOfAccounts() throws Exception{
//
//        //Does the list not need to be entered into the database first?
//        //We are expecting a list of results back
//
//        //Given
//        List<CustomerDto> customers = List.of(
//                new CustomerDto("Mariam", "mariam@example.com", "0121"),
//                new CustomerDto("Ahsen", "ahsen@example.com", "0111"),
//                new CustomerDto("Ali", "ali@example.com", "0222"),
//                new CustomerDto("Noor", "noor@example.com", "0333")
//        );
//
//        //When
//        when(accountServiceImpl.findAllAccounts()).thenReturn(customers);
//
//        mockMvc.perform(get("/api/accounts"))
//                .andExpectAll(
//                        status().isOk()
//        );
//    }

}