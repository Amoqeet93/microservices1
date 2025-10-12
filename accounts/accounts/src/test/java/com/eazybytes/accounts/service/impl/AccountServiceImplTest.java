package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.model.Accounts;
import com.eazybytes.accounts.model.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    AccountsRepository accountsRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    public void shouldCreateAccount() throws Exception {
        CustomerDto customerDto = new CustomerDto("Moqeet", "moqeet@example.com", "01212");
        Customer savedCustomer = new Customer();

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        accountService.createAccount(customerDto);

        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(accountsRepository, times(1)).save(any(Accounts.class));
    }

}