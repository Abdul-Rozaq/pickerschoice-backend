package com.pickerschoice.pickerschoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pickerschoice.pickerschoice.dto.OrderResponse;
import com.pickerschoice.pickerschoice.service.OrderService;

@RestController
@RequestMapping("management/api/v1/orders")
public class OrderManagementController {

    private OrderService orderService;

    @Autowired
    public OrderManagementController(OrderService orderService) {
        this.orderService = orderService;
    }

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
    public ResponseEntity<List<OrderResponse>> getAllOrdersForCustomer(@PathVariable("customerId") int customerId) {
        return new ResponseEntity<>(orderService.findAllByCustomerId(customerId), HttpStatus.OK);
    }
}
