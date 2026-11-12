package com.banking.banking_system.constants;

public class ApplicationConstants {
    private ApplicationConstants() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    public static final String JWT_SECRET_KEY = "JWT_SECRET";
    public static final String JWT_SECRET_DEFAULT_VALUE =
            System.getenv("JWT_SECRET");
    public static final String JWT_HEADER = "Authorization";
}
