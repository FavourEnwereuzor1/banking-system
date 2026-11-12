package com.banking.banking_system.aspect;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAndPerformanceAspect {

    @Around("execution(* com.banking.banking_system..service..*(..))")
    public Object logAndMeasureExecutionTime(
            ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();

        log.info("Entering method: {}", methodName);

        try {
            Object result = joinPoint.proceed();

            log.info("Method executed successfully: {}", methodName);

            return result;
        } catch (Throwable ex) {
            log.error("Method failed: {} - {}",
                    methodName, ex.getClass().getSimpleName());

            throw ex;
        } finally {
            long executionTime =
                    System.currentTimeMillis() - startTime;

            log.info("Execution time for {}: {} ms",
                    methodName, executionTime);
        }
    }
}
