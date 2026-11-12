package com.banking.banking_system.customer.repository;

import com.banking.banking_system.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);
}
