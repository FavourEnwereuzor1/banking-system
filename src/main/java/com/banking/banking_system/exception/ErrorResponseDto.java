package com.banking.banking_system.exception;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public record ErrorResponseDto(String apiPath,
                               HttpStatus errorCode,
                               String errorMessage,
                               LocalDateTime errorTim) {
}
