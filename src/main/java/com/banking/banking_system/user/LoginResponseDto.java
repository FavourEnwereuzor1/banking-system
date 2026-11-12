package com.banking.banking_system.user;

public record LoginResponseDto (String message,
        UserDto user,
        String jwtToken) {
}
