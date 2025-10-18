package com.example.Loans.controller;

import com.example.Loans.constants.LoanConstants;
import com.example.Loans.dto.LoansDto;
import com.example.Loans.dto.ResponseDto;
import com.example.Loans.service.serviceImpl.LoansServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor

public class LoansController {

    private LoansServiceImpl loansService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(String mobileNumber){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoanConstants.MESSAGE_201, LoanConstants.STATUS_201));

    }
}
