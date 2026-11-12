package com.banking.banking_system.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoginSuccessAuditAspect {

    @AfterReturning(
            pointcut = "execution(* com.banking.banking_system..*.apiLogin(..))",
            returning = "response"
    )
    public void auditSuccessfulLogin(Object response) {

        if (response instanceof ResponseEntity<?> responseEntity
                && responseEntity.getStatusCode().is2xxSuccessful()) {

            log.info("User logged in successfully");
        }
    }
}