package com.pickerschoice.pickerschoice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pickerschoice.pickerschoice.dto.CustomerDto;
import com.pickerschoice.pickerschoice.mapper.CustomerMapper;
import com.pickerschoice.pickerschoice.model.Customer;
import com.pickerschoice.pickerschoice.repository.CustomerRepository;
import com.pickerschoice.pickerschoice.security.AppUserRole;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
	
	@Mock
    private CustomerRepository customerRepository;
	@Mock
    private CustomerMapper customerMapper;
	@Captor
	private ArgumentCaptor<Customer> customerArgumentCaptor;
	private CustomerService customerService;
	
	@BeforeEach
	public void setup() {
		customerService = new CustomerService(customerRepository, customerMapper);
	}
	
	@Test
	@DisplayName("Should retrieve customer based on customer id")
	public void shouldFindCustomerById() {
		Customer customer = new Customer(1, "First name", "Last name", "customer@email", "090283664837", "Address", "Pass", AppUserRole.USER, false, false);
		CustomerDto expectedResponse = new CustomerDto(1, "First name", "Last name", "090283664837", "Address", "customer@email", AppUserRole.USER, false);
		
		Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
		Mockito.when(customerMapper.mapToDto(Mockito.any(Customer.class))).thenReturn(expectedResponse);
		
		CustomerDto actualResponse = customerService.findById(1);
		
		Assertions.assertThat(actualResponse.getCustomerId()).isEqualTo(expectedResponse.getCustomerId());
		Assertions.assertThat(actualResponse.getFirstName()).isEqualTo(expectedResponse.getFirstName());
	}
	
	@SuppressWarnings({ "deprecation", "unlikely-arg-type" })
	@Test
	@DisplayName("Should fetch all users in the database")
	public void shouldRetrieveAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		List<CustomerDto> expectedCustomers = new ArrayList<>();
		
		Customer customer1 = new Customer(1, "First name", "Last name", "customer@email", "090283664837", "Address", "Pass", AppUserRole.USER, false, false);
		Customer customer2 = new Customer(2, "First name 2", "Last name 2", "customer2@email", "090283664830", "Address 2", "Pass 2", AppUserRole.USER, false, false);
	
		customers.add(customer1);
		customers.add(customer2);
		
		expectedCustomers.addAll(customerMapper.mapAllToDto(customers));
		
		Mockito.when(customerRepository.findAll()).thenReturn(customers);
		Mockito.when(customerMapper.mapAllToDto(Mockito.anyListOf(Customer.class))).thenReturn(expectedCustomers);
		
		List<CustomerDto> actualResponse = customerService.findAll();
		
		Assertions.assertThat(actualResponse.size()).isEqualTo(expectedCustomers.size());
		Assertions.assertThat(actualResponse.contains(customer1)).isEqualTo(expectedCustomers.contains(customer1));
		Assertions.assertThat(actualResponse.contains(customer2)).isEqualTo(expectedCustomers.contains(customer2));
	}
	
	@Test
	@DisplayName("Should retrive a customer based on an email")
	public void shouldRetriveCustomerByEmail() {
		Customer customer = new Customer(1, "First name", "Last name", "customer@email", "090283664837", "Address", "Pass", AppUserRole.USER, false, false);
		CustomerDto expectedResponse = new CustomerDto(1, "First name", "Last name", "090283664837", "Address", "customer@email", AppUserRole.USER, false);
		
		Mockito.when(customerRepository.findByEmail("customer@email")).thenReturn(Optional.of(customer));
		Mockito.when(customerMapper.mapToDto(Mockito.any(Customer.class))).thenReturn(expectedResponse);
		
		CustomerDto actualResponse = customerService.findByEmail("customer@email");
		
		Assertions.assertThat(actualResponse.getCustomerId()).isEqualTo(expectedResponse.getCustomerId());
		Assertions.assertThat(actualResponse.getEmail()).isEqualTo(expectedResponse.getEmail());
	}

}
