package com.pickerschoice.pickerschoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pickerschoice.pickerschoice.dto.CustomerDto;
import com.pickerschoice.pickerschoice.dto.OrderRequest;
import com.pickerschoice.pickerschoice.dto.OrderResponse;
import com.pickerschoice.pickerschoice.service.CustomerService;
import com.pickerschoice.pickerschoice.service.OrderService;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private CustomerService customerService;
    private OrderService orderService;

    @Autowired
    public CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }
    
    // GET ALL CUSTOMERS
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
    	return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
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

    /* FETCH ALL ORDERS FOR A CUSTOMER */
    @GetMapping("/{customerId}/orders")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<OrderResponse>> getAllOrdersForCustomer(@PathVariable("customerId") int customerId) {
        return new ResponseEntity<>(orderService.findAllByCustomerId(customerId), HttpStatus.OK);
    }

    /* FETCH A SINGLE ORDER FOR A CUSTOMER */
    @GetMapping("/{customerId}/orders/{orderId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable("customerId") int customerId, 
            @PathVariable("orderId") int orderId
    ) {
        return new ResponseEntity<>(orderService.findByCustomerIdAndOrderId(customerId, orderId), HttpStatus.OK);
    }

    /* CREATE AN ORDER FOR A CUSTOMER */
    @PostMapping("/{customerId}/order")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<OrderResponse> createOrder(
            @PathVariable("customerId") int customerId,
            @RequestBody OrderRequest request
    ) {
        return new ResponseEntity<>(orderService.saveOrder(customerId, request), HttpStatus.CREATED);
    }
}
