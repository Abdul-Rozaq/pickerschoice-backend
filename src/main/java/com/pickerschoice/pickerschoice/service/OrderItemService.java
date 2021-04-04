package com.pickerschoice.pickerschoice.service;

import com.pickerschoice.pickerschoice.dto.OrderItemResponse;
import com.pickerschoice.pickerschoice.mapper.OrderItemsMapper;
import com.pickerschoice.pickerschoice.model.OrderItem;
import com.pickerschoice.pickerschoice.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemService {

    private OrderItemRepository orderItemRepository;
    private OrderService orderService;
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, OrderService orderService, OrderItemsMapper orderItemsMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.orderItemsMapper = orderItemsMapper;
    }

    // FETCH ORDER ITEMS BELONGING TO AN ORDER BY ORDER-ID
    @Transactional
    public List<OrderItemResponse> findByOrderId(int orderId) {
        orderService.findById(orderId);
        List<OrderItem> orderItems = orderItemRepository.findAllByOrder_OrderId(orderId);

        return orderItemsMapper.mapToEntity(orderItems);
    }
}
