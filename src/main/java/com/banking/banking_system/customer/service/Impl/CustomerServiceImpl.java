package com.banking.banking_system.customer.service.Impl;

import com.banking.banking_system.customer.dto.CustomerDto;
import com.banking.banking_system.customer.entity.Customer;
import com.banking.banking_system.customer.repository.CustomerRepository;
import com.banking.banking_system.customer.service.ICustomerService;
import com.banking.banking_system.exception.CustomerNotFoundException;
import com.banking.banking_system.exception.EmailAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private CustomerRepository customerRepository;


    @Override
    public CustomerDto saveCustomer(CustomerDto dto) {
        if (customerRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException(dto.email());
        }
        Customer customer = new Customer();
        updateCustomerFields(customer, dto);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDto(savedCustomer);
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()
                        -> new CustomerNotFoundException(id));

        if (!customer.getEmail().equals(dto.email())
                && customerRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException(dto.email());
        }
        updateCustomerFields(customer, dto);
        Customer updatedCustomer = customerRepository.save(customer);

        return convertToDto(updatedCustomer);
    }

    @Override
    public CustomerDto patchCustomer(Long id, CustomerDto dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException(id));
        if (dto.firstName() != null) {
            customer.setFirstName(dto.firstName());
        }
        if (dto.lastName() != null) {
            customer.setLastName(dto.lastName());
        }

        if (dto.email() != null) {
            if (!customer.getEmail().equals(dto.email())
                    && customerRepository.existsByEmail(dto.email())) {
                throw new EmailAlreadyExistsException(dto.email());
            }
        }
        if (dto.phoneNumber() != null) {
            customer.setPhoneNumber(dto.phoneNumber());
        }

        Customer updatedCustomer = customerRepository.save(customer);

        return convertToDto(updatedCustomer);

    }

    @Override
    public CustomerDto findCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException(id));

        return convertToDto(customer);
    }


    @Override
    public List<CustomerDto> findAllCustomer() {
        List<Customer> result = customerRepository.findAll();
        return result
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        customerRepository.deleteById(id);
    }


    @Override
    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }

    // HelperMethod
    private void updateCustomerFields(Customer customer, CustomerDto dto) {
        customer.setFirstName(dto.firstName());
        customer.setLastName(dto.lastName());
        customer.setEmail(dto.email());
        customer.setPhoneNumber(dto.phoneNumber());
    }

    private CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getCreatedAt()
        );
    }
}

