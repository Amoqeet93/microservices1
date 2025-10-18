package com.example.Loans.service.serviceImpl;

import com.example.Loans.dto.LoansDto;
import com.example.Loans.exception.LoansAlreadyExistsException;
import com.example.Loans.exception.ResourceNotFoundException;
import com.example.Loans.model.Loans;
import com.example.Loans.repository.LoansRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoansServiceImplTest {

    @Mock
    LoansRepository loansRepository;

    @InjectMocks
    LoansServiceImpl loansService;


    @Test
    public void createLoanSuccessfully() throws Exception {
        //Given
        LoansDto loansDto = new LoansDto();
        Loans loans = new Loans();

        //when
        when(loansRepository.save(any(Loans.class))).thenReturn(loans);

        loansService.createLoan(anyString());

        //Then
        verify(loansRepository, times(1)).save(any(Loans.class));

    }

    @Test
    public void shouldThrowErrorWhenCreatingLoans() throws LoansAlreadyExistsException {
        //Given
        Loans existingloans = new Loans();

        //When
        when(loansRepository.findByMobileNumber(anyString())).thenReturn(Optional.of(existingloans));

        assertThrows(LoansAlreadyExistsException.class,
                () -> loansService.createLoan(anyString()));

        //Then
        verify(loansRepository, never()).save(any(Loans.class));
    }

    @Test
    public void shouldFetchLoan() throws Exception {
        //Given
        Loans exitingLoan = new Loans();
        //When
        when(loansRepository.findByMobileNumber(anyString())).thenReturn(Optional.of(exitingLoan));

        loansService.fetchLoan("01212");

        //Then
        verify(loansRepository, times(1)).findByMobileNumber("01212");
    }

    @Test
    public void shouldThrowErrorWhenFetchingLoans() throws Exception{
        when(loansRepository.findByMobileNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> loansService.fetchLoan(anyString()));

        verify(loansRepository, never()).findByMobileNumber("01212");
    }

    @Test
    public void shouldDeleteLoan() throws Exception {
        //Given
        Loans loans = new Loans();
        //When
        when(loansRepository.findByMobileNumber(anyString())).thenReturn((Optional.of(loans)));

        loansService.deleteLoan("01212");
        //Then
        verify(loansRepository, times(1)).findByMobileNumber("01212");
    }

    @Test
    public void shouldThrowErrorWhenDeletingLoans() throws Exception{
        Loans loans = new Loans();

        when(loansRepository.findByMobileNumber(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> loansService.deleteLoan("01212"));

        verify(loansRepository, never()).delete(any(Loans.class));
    }

    @Test
    public void shouldUpdateExistingLoans() throws Exception {
        Loans existingLoan = new Loans();
        LoansDto loansDto = new LoansDto();

        when(loansRepository.findByLoanNumber(loansDto.getLoanNumber())).thenReturn(Optional.of(existingLoan));
        when(loansRepository.save(any())).thenReturn(existingLoan);

        loansService.updateLoan(loansDto);

        verify(loansRepository, times(1)).save(any(Loans.class));
    }

    @Test
    public void shouldThrowResourceNotFoundError() throws ResourceNotFoundException{
        LoansDto loansDto = new LoansDto();

        when(loansRepository.findByLoanNumber(loansDto.getLoanNumber())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> loansService.updateLoan(loansDto));

        verify(loansRepository,never()).findByLoanNumber("01212");
    }
}