package com.pickerschoice.pickerschoice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pickerschoice.pickerschoice.dto.OrderRequest;
import com.pickerschoice.pickerschoice.dto.OrderResponse;
import com.pickerschoice.pickerschoice.service.OrderService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    /* FETCH ALL ORDERS */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return new ResponseEntity<>(orderService.findAllOrders(), HttpStatus.OK);
    }

    /* FETCH A SINGLE ORDER */
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<OrderResponse> findById(@PathVariable("orderId") int orderId) {
        return new ResponseEntity<>(orderService.findById(orderId), HttpStatus.OK);
    }

    /* FETCH ALL ORDERS FOR A CUSTOMER */
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<OrderResponse>> getAllOrdersForCustomerById(@PathVariable("customerId") int customerId) {
        return new ResponseEntity<>(orderService.findAllByCustomerId(customerId), HttpStatus.OK);
    }
    
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<OrderResponse>> getAllOrdersForCustomer() {
        return new ResponseEntity<>(orderService.findAllOrderForCustomer(), HttpStatus.OK);
    }
    
    /* UPDATE AN ORDER */
    @PutMapping("/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable("orderId") int orderId) {
    	return new ResponseEntity<>(orderService.updateOrderStatus(orderId), HttpStatus.OK);
    }
 
    /* CREATE AN ORDER FOR A CUSTOMER */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody OrderRequest request
    ) {
        return new ResponseEntity<>(orderService.saveOrder(request), HttpStatus.CREATED);
    }
}
