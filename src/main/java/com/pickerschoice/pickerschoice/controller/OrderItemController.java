package com.pickerschoice.pickerschoice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pickerschoice.pickerschoice.dto.OrderItemResponse;
import com.pickerschoice.pickerschoice.service.OrderItemService;

@RestController
@RequestMapping("api/v1/order-items")
public class OrderItemController {

    private OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<OrderItemResponse>> getOrderItems(
            @PathVariable("orderId") int orderId
    ) {
        return new ResponseEntity<>(orderItemService.findByOrderId(orderId), HttpStatus.OK);
    }
}
