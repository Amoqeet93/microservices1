package com.example.Loans.dto;

import java.time.LocalDateTime;

public class ErrorResponseDto {

    private String errorCode;

    private String apiPath;

    private LocalDateTime errorTime;

    private String errorMessage;
}
