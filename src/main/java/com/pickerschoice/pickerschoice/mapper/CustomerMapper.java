package com.pickerschoice.pickerschoice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pickerschoice.pickerschoice.dto.CustomerDto;
import com.pickerschoice.pickerschoice.model.Customer;

@Component
public class CustomerMapper {

	public CustomerDto mapToDto(Customer customer) {
		return new CustomerDto(
    		customer.getCustomerId(),
    		customer.getFirstName(), 
    		customer.getLastName(), 
    		customer.getPhone(), 
    		customer.getAddress(), 
    		customer.getEmail(), 
    		customer.getAppUserRole(),
    		customer.getEnabled()
    	);
	}
	
	public List<CustomerDto> mapAllToDto(List<Customer> customers) {
		return customers
		    		.stream()
		    		.map(customer -> mapToDto(customer))
		    		.collect(Collectors.toList());
	}
}
