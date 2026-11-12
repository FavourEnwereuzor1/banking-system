package com.banking.banking_system.customer.service;

import com.banking.banking_system.customer.dto.CustomerDto;
import java.util.List;
public interface ICustomerService {

    CustomerDto saveCustomer(CustomerDto dto);

    CustomerDto updateCustomer(Long id, CustomerDto dto);

    CustomerDto findCustomerById(Long id);

    List<CustomerDto> findAllCustomer();

    void deleteById(Long id);

    CustomerDto patchCustomer(Long id, CustomerDto dto);
    
    boolean existsById(Long id);
}
