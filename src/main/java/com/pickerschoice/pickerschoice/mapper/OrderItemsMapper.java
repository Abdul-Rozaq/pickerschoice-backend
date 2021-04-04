package com.pickerschoice.pickerschoice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pickerschoice.pickerschoice.dto.OrderItemResponse;
import com.pickerschoice.pickerschoice.model.OrderItem;

@Component
public class OrderItemsMapper {
	
	public OrderItemResponse mapToOrderItemResponse(OrderItem orderItem) {
		return new OrderItemResponse(
                orderItem.getProductName(),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
	}

	public List<OrderItemResponse> mapToEntity(List<OrderItem> orderItems) {
		return orderItems
                .stream()
                .map(orderItem -> mapToOrderItemResponse(orderItem))
                .collect(Collectors.toList());
	}
}
