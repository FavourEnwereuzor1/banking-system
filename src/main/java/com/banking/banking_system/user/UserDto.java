package com.banking.banking_system.user;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserDto {
    private Long userId;
    private Long customerId;
    private String email;
    private String role;
    private Instant createdAt;
}