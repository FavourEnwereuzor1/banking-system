package com.banking.banking_system.security;

import com.banking.banking_system.security.filter.JwtTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class BankSystemSecurityConfig {

    @Bean
    SecurityFilterChain customSecurityFilterChain(HttpSecurity http, List<String> publicPaths, List<String> securedPaths) {
        return http.csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests(request ->{
                    publicPaths.forEach(path -> request.requestMatchers(path).permitAll());
                    securedPaths.forEach(path -> request.requestMatchers(path).authenticated());
                    request.anyRequest().denyAll();

                })
                .addFilterBefore(new JwtTokenValidatorFilter(publicPaths), BasicAuthenticationFilter.class)
                .formLogin(form -> form.disable())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            BankingUsernamePasswordAuthenticationProvider provider) {
        return new ProviderManager(provider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
