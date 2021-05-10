package com.pickerschoice.pickerschoice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pickerschoice.pickerschoice.dto.OrderResponse;
import com.pickerschoice.pickerschoice.model.Order;

@Component
public class OrderMapper {

    public OrderResponse mapToDto(Order order) {
        return new OrderResponse(
                order.getOrderId(), order.getCustomer().getCustomerId(), order.getCustomerName(),
                order.getPhone(), order.getAddress(), order.getOrderDate(), order.getTotal(),
                order.getStatus(), order.getPaid()
        );
    }
    
    public List<OrderResponse> mapAllToDto(List<Order> orders) {
    	return orders
			        .stream()
			        .map(order -> mapToDto(order))
			        .collect(Collectors.toList());
    }
}
