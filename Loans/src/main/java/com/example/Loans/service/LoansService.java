package com.example.Loans.service;

import com.example.Loans.dto.LoansDto;
import com.example.Loans.model.Loans;

public interface LoansService {

    void createLoan(String mobileNumber);

    LoansDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoansDto loansDto);

    boolean deleteLoan(String mobileNumber);
}
