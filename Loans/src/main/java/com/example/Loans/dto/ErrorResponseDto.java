package com.example.Loans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ErrorResponseDto {

    private HttpStatus errorCode;

    private String apiPath;

    private LocalDateTime errorTime;

    private String errorMessage;
}
