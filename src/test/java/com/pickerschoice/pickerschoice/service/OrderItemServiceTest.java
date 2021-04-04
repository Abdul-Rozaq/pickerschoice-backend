package com.pickerschoice.pickerschoice.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pickerschoice.pickerschoice.dto.OrderItemResponse;
import com.pickerschoice.pickerschoice.mapper.OrderItemsMapper;
import com.pickerschoice.pickerschoice.model.OrderItem;
import com.pickerschoice.pickerschoice.repository.OrderItemRepository;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {
	public static final int ORDER_ID = 1;
	
	@Mock
    private OrderItemRepository orderItemRepository;
	@Mock
    private OrderService orderService;
	@Mock
    private OrderItemsMapper orderItemsMapper;
	
	private OrderItemService orderItemService;
	
	@BeforeEach
	public void setup() {
		orderItemService = new OrderItemService(orderItemRepository, orderService, orderItemsMapper);
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Should fetch all order items by an order ID")
	public void shouldFetchAllOrderItems() {
		OrderItem orderItem1 = new OrderItem("Product 1", 2, 50.0);
		OrderItem orderItem2 = new OrderItem("Product 2", 3, 20.0);
		
		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(orderItem1);
		orderItems.add(orderItem2);
		
		List<OrderItemResponse> orderItemResponses = new ArrayList<>();
		orderItemResponses.add(orderItemsMapper.mapToOrderItemResponse(orderItem1));
		orderItemResponses.add(orderItemsMapper.mapToOrderItemResponse(orderItem2));
		
		Mockito.when(orderItemService.findByOrderId(ORDER_ID)).thenReturn(orderItemResponses);
		Mockito.when(orderItemRepository.findAllByOrder_OrderId(ORDER_ID)).thenReturn(orderItems);
		Mockito.when(orderItemsMapper.mapToEntity(Mockito.anyListOf(OrderItem.class))).thenReturn(orderItemResponses);
		
		List<OrderItemResponse> actualResponse = orderItemService.findByOrderId(1);
		
		Assertions.assertThat(actualResponse.size()).isEqualTo(orderItemResponses.size());
	}
	
//	@Test
//	@DisplayName("Should save order item")
//	public void shouldSaveOrderItem() {
//		OrderItem expectedOrderItem = new OrderItem(1, null, "Product 1", 2, 50.0);
//		OrderItem orderItemRequest = new OrderItem("Product 1", 2, 50.0);
//		
//		Mockito.when(orderItemRepository.save(orderItemRequest)).thenReturn(expectedOrderItem);
//		OrderItem actualResponse = orderItemRepository.save(orderItemRequest);
//		Mockito.verify(orderItemRepository, Mockito.times(1)).save(orderItemRequest);
//		
//		Assertions.assertThat(actualResponse.getOrderItemId()).isEqualTo(expectedOrderItem.getOrderItemId());
//		Assertions.assertThat(actualResponse.getProductName()).isEqualTo(expectedOrderItem.getProductName());
//	}
}
