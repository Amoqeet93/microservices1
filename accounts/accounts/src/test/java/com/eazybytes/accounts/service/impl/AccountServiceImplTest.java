package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.model.Accounts;
import com.eazybytes.accounts.model.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
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
        CustomerDto customerDto = new CustomerDto();
        Customer savedCustomer = new Customer();

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        accountService.createAccount(customerDto);

        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(accountsRepository, times(1)).save(any(Accounts.class));
    }

    @Test
    public void shouldThrowErrorWhenCustomerAlreadyExists() throws CustomerAlreadyExistsException{
        CustomerDto customerDto = new CustomerDto();
        Customer existingCustomer = new Customer();

        when(customerRepository.findByMobileNumber(customerDto.getMobileNumber()))
                .thenReturn(Optional.of(existingCustomer));

        assertThrows(
                CustomerAlreadyExistsException.class,
                () -> accountService.createAccount(customerDto)
        );

        verify(customerRepository, never()).save(any(Customer.class));
        verify(accountsRepository, never()).save(any(Accounts.class));
    }

    @Test
    public void shouldGetAccount_WhenCustomerExists() throws Exception{
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setMobileNumber("0121");

        Accounts accounts = new Accounts();
        accounts.setAccountNumber(123L);
        accounts.setCustomerId(1L);

        when(customerRepository.findByMobileNumber(customer.getMobileNumber())).thenReturn(Optional.of(customer));
        when(accountsRepository.findByCustomerId(customer.getCustomerId())).thenReturn(Optional.of(accounts));

        CustomerDto result = accountService.fetchAccount(customer.getMobileNumber());

        assertNotNull(result);
        verify(customerRepository).findByMobileNumber(customer.getMobileNumber());
        verify(accountsRepository).findByCustomerId(customer.getCustomerId());
    }

    @Test
    public void shouldThrowResourceNotFoundErrorWhenCustomerNotFound () throws ResourceNotFoundException {
        String mobileNumber = "0121";

        when(customerRepository.findByMobileNumber(mobileNumber)).thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(ResourceNotFoundException.class,
                () -> accountService.fetchAccount("0121"));

        assertEquals("Customer not found with the given input data mobileNumber : '0121'", exception.getMessage());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenAccountNotFound() throws ResourceNotFoundException{
        Customer customer = new Customer();
        customer.setCustomerId(123L);
        customer.setMobileNumber("0121");

        when(customerRepository.findByMobileNumber(customer.getMobileNumber())).thenReturn(Optional.of(customer));
        when(accountsRepository.findByCustomerId(customer.getCustomerId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(ResourceNotFoundException.class, () -> accountService.fetchAccount(customer.getMobileNumber()));

        assertEquals("Account not found with the given input data customerId : '123'", exception.getMessage());
    }

    @Test
    public void accountUpdatedSuccessfully() throws Exception {
        //Given
        //Need both the account and customer objects
        CustomerDto customerDto = new CustomerDto();

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(2L);
        customerDto.setAccountsDto(accountsDto);

        Accounts accounts = new Accounts();
        accounts.setCustomerId(1L);

        Customer customer = new Customer();

        //When
        when(accountsRepository.findById(anyLong())).thenReturn(Optional.of(accounts));
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        //Need to save as it's an update
        when(accountsRepository.save(any(Accounts.class))).thenReturn(accounts);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        boolean result = accountService.updateAccount(customerDto);

        //Then
        assertEquals(result, true);
    }

    @Test
    public void deleteAccountSuccessfully() throws  Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setMobileNumber("0123456789");

        when(customerRepository.findByMobileNumber(anyString())).thenReturn(Optional.of(customer));

        boolean result = accountService.deleteAccount(customer.getMobileNumber());

        assertEquals(result, true);
    }

    @Test
    public void shouldThrowErrorWhenDeletingAccount() throws Exception {
        when(customerRepository.findByMobileNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()-> accountService.deleteAccount("02112"));
    }




}