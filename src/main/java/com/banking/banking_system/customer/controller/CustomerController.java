package com.banking.banking_system.customer.controller;

import com.banking.banking_system.customer.dto.CustomerDto;
import com.banking.banking_system.customer.service.Impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
@Validated
public class CustomerController {
    private final CustomerServiceImpl customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> saveCustomer(@RequestBody @Valid CustomerDto customerDto){
     CustomerDto customerDto1 = customerService.saveCustomer(customerDto);
     return  ResponseEntity.status(HttpStatus.CREATED)
             .body(customerDto1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findCustomerById(@PathVariable @Positive(message = "Customer ID must be positive")
                                                            Long id){
        CustomerDto customerDto = customerService.findCustomerById(id);
        return  ResponseEntity.ok(customerDto);
    }

    @GetMapping("")
    public ResponseEntity<List<CustomerDto>> findAllCustomer(){
        List<CustomerDto> customers =
                customerService.findAllCustomer();

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable @Positive(message = "Customer ID must be positive")Long id) {
        boolean exists = customerService.existsById(id);
        return ResponseEntity.ok(exists);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> UpdateCustomer(
            @PathVariable  @Positive(message = "Customer ID must be positive") Long id,
            @RequestBody @Valid CustomerDto dto) {

        CustomerDto updatedCustomer =
                customerService.updateCustomer(id,dto);

        return ResponseEntity.ok(updatedCustomer);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDto> patchCustomer(
            @PathVariable  @Positive(message = "Customer ID must be positive")Long id,
            @RequestBody @Valid CustomerDto dto) {

        CustomerDto updatedCustomer =
                customerService.patchCustomer(id, dto);

        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @Positive(message = "Customer ID must be positive") Long id){
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
