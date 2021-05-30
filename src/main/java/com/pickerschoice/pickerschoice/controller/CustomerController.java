package com.pickerschoice.pickerschoice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pickerschoice.pickerschoice.dto.CustomerDto;
import com.pickerschoice.pickerschoice.mapper.CustomerMapper;
import com.pickerschoice.pickerschoice.model.Customer;
import com.pickerschoice.pickerschoice.service.AuthService;
import com.pickerschoice.pickerschoice.service.CustomerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;
    private AuthService authService;
    private CustomerMapper customerMapper;
    
    // GET ALL CUSTOMERS
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
    	return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }
    
    // GET currently logged in user
    @GetMapping("/me")
    public ResponseEntity<CustomerDto> getCurrentUser() {
    	Customer customer = authService.getCurrentCustomer();
        return new ResponseEntity<>(customerMapper.mapToDto(customer), HttpStatus.OK);
    }

    // GET A CUSTOMER BY ID
    @GetMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerId") int customerId) {
        return new ResponseEntity<>(customerService.findById(customerId), HttpStatus.OK);
    }
    
    // GET A single CUSTOMER BY EMAIL
    @GetMapping("/email/{customerEmail}")
    public ResponseEntity<CustomerDto> getCustomerByEmail(@PathVariable("customerEmail") String customerEmail) {
        return new ResponseEntity<>(customerService.findByEmail(customerEmail), HttpStatus.OK);
    }

}
