package com.pickerschoice.pickerschoice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pickerschoice.pickerschoice.dto.CustomerDto;
import com.pickerschoice.pickerschoice.exception.AppAuthException;
import com.pickerschoice.pickerschoice.mapper.CustomerMapper;
import com.pickerschoice.pickerschoice.model.Customer;
import com.pickerschoice.pickerschoice.repository.CustomerRepository;

@Service
public class CustomerService {
    private static final String CUSTOMER_NOT_FOUND = "Customer %s not found";

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    // FIND A CUSTOMER BY ID
    @Transactional
    public CustomerDto findById(int customerId) {
    	Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new AppAuthException(String.format(CUSTOMER_NOT_FOUND, customerId))
        );
    	
    	return customerMapper.mapToDto(customer);
    }

    // FETCH ALL CUSTOMERS
    @Transactional
	public List<CustomerDto> findAll() {
    	List<Customer> customers = customerRepository.findAll();
    	
    	return customerMapper.mapAllToDto(customers);
	}

	// FIND CUSTOMER BY EMAIL
	@Transactional
	public CustomerDto findByEmail(String customerEmail) {
		Customer customer = customerRepository.findByEmail(customerEmail).orElseThrow(
				() -> new AppAuthException(String.format("Customer with email %s not found", customerEmail))
		);
		
		return customerMapper.mapToDto(customer);
	}
	
}
