package com.banking.banking_system.auth;

import com.banking.banking_system.customer.entity.Customer;
import com.banking.banking_system.customer.repository.CustomerRepository;
import com.banking.banking_system.security.util.JwtUtil;
import com.banking.banking_system.user.*;
import com.banking.banking_system.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDto> apiLogin(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            var resultAuthentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.email(),
                    loginRequestDto.password()));
            // Generate JWT token
            String jwtToken = jwtUtil.generateJwtToken(resultAuthentication);
            User user = (User) resultAuthentication.getPrincipal();
            UserDto userDto = new UserDto();
            userDto.setUserId(user.getId());
            userDto.setCustomerId(user.getCustomer().getId());
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole());
            userDto.setCreatedAt(user.getCreatedAt());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(),
                            userDto, jwtToken));
        } catch (BadCredentialsException ex) {
            log.warn("Login failed: invalid credentials");

            return buildErrorResponse(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password"
            );

        } catch (AuthenticationException ex) {
            log.warn("Authentication failed");

            return buildErrorResponse(
                    HttpStatus.UNAUTHORIZED,
                    "Authentication failed"
            );

        } catch (Exception ex) {
            log.error("Unexpected error during login", ex);

            return buildErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred"
            );
        }

    }

    @PostMapping("/register/public")
    @Transactional
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterRequestDto registerRequestDto) {

        String email = registerRequestDto.email().trim();

        // Check whether the email already belongs to a customer
        if (customerRepository.existsByEmailIgnoreCase(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "email", "Email is already registered"
                    ));
        }

        // Check whether a login account already uses this email
        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "email", "Email is already registered"
                    ));
        }

        // 1. Create the customer
        Customer customer = new Customer();
        customer.setFirstName(registerRequestDto.firstName().trim());
        customer.setLastName(registerRequestDto.lastName().trim());
        customer.setEmail(email);
        customer.setPhoneNumber(registerRequestDto.phoneNumber());

        Customer savedCustomer = customerRepository.save(customer);

        // 2. Create the user's login account
        User user = new User();
        user.setEmail(email);

        // Never save the raw password
        user.setPassword(
                passwordEncoder.encode(registerRequestDto.password())
        );

        // Assign the role on the backend
        user.setRole("USER");

        // Link the login account to its customer
        user.setCustomer(savedCustomer);

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "User registered successfully"
                ));
    }


    private ResponseEntity<LoginResponseDto> buildErrorResponse(HttpStatus status,
                                                                String message) {
        return ResponseEntity
                .status(status)
                .body(new LoginResponseDto(message, null, null));
    }
}
