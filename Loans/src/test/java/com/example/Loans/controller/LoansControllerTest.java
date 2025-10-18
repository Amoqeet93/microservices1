package com.example.Loans.controller;

import com.example.Loans.dto.LoansDto;
import com.example.Loans.exception.LoansAlreadyExistsException;
import com.example.Loans.service.serviceImpl.LoansServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ContextConfiguration(classes = LoansController.class)
class LoansControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    LoansServiceImpl loansService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void createLoan() throws Exception {
        //Given
        LoansDto loansDto = new LoansDto();
        loansDto.setMobileNumber("01212");

        //When
        doNothing().when(loansService).createLoan(anyString());

        //Then
        mockMvc.perform(post("/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .param("mobileNumber", loansDto.getMobileNumber())
        ).andExpectAll(
                status().isCreated(),
                jsonPath("$.statusMsg").value("Loan created successfully"),
                jsonPath("$.statusCode").value("201")
        );
    }

    @Test
    public void shouldThrowBadRequestStatus() throws Exception {
        LoansDto loansDto = new LoansDto();

        //When
        doThrow(new LoansAlreadyExistsException(""))
                .when(loansService).createLoan(anyString());

        mockMvc.perform(post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("mobileNumber",loansDto.getMobileNumber())
                )
                .andExpectAll(
                        status().isBadRequest()
                );

    }




}