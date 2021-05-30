package com.pickerschoice.pickerschoice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pickerschoice.pickerschoice.dto.OrderRequest;
import com.pickerschoice.pickerschoice.dto.OrderResponse;
import com.pickerschoice.pickerschoice.exception.AppAuthException;
import com.pickerschoice.pickerschoice.mapper.OrderMapper;
import com.pickerschoice.pickerschoice.model.Customer;
import com.pickerschoice.pickerschoice.model.Order;
import com.pickerschoice.pickerschoice.repository.CustomerRepository;
import com.pickerschoice.pickerschoice.repository.OrderItemRepository;
import com.pickerschoice.pickerschoice.repository.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
    private static final String CUSTOMER_NOT_FOUND = "Customer %s not found";
    private static final String ORDER_NOT_FOUND = "Order %s not found";

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private OrderItemRepository orderItemRepository;
    private OrderMapper orderMapper;
    private AuthService authService;

    /* CREATE A NEW ORDER */
    @Transactional
    public OrderResponse saveOrder(OrderRequest request) {
        Customer customer = authService.getCurrentCustomer();
        
        customer.createOrder(request.getOrder());
        request.getOrder().setStatus("pending");
        Order _order = orderRepository.save(request.getOrder());

        request.getOrderItems().forEach(orderItem -> {
            _order.addItems(orderItem);
            orderItemRepository.save(orderItem);
        });
        
        return orderMapper.mapToDto(_order);
    }

    /* FETCH ALL ORDERS */
    @Transactional
    public List<OrderResponse> findAllOrders() {
        return orderRepository
        		.findAll()
        		.stream()
        		.map(orderMapper::mapToDto)
        		.collect(Collectors.toList());
    }

    /* FETCH AN ORDER BY ORDER-ID */
    @Transactional
    public OrderResponse findById(int orderId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new AppAuthException(String.format(ORDER_NOT_FOUND, orderId)));

        return orderMapper.mapToDto(order);
    }

    /* FETCH ALL ORDERS FOR A CUSTOMER USING CUSTOMER ID */
    @Transactional
    public List<OrderResponse> findAllByCustomerId(int customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new AppAuthException(String.format(CUSTOMER_NOT_FOUND, customerId)));

        List<Order> customerOrderList = orderRepository.findAllByCustomer_CustomerId(customer.getCustomerId());
        return orderMapper.mapAllToDto(customerOrderList);
    }
    
    @Transactional
    public List<OrderResponse> findAllOrderForCustomer() {
        Customer customer = authService.getCurrentCustomer();

        List<Order> customerOrderList = orderRepository.findAllByCustomer_CustomerId(customer.getCustomerId());
        return orderMapper.mapAllToDto(customerOrderList);
    }

    /* FETCH AN ORDER FOR A CUSTOMER */
    @Transactional
    public OrderResponse findByCustomerIdAndOrderId(int customerId, int orderId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new AppAuthException(String.format(CUSTOMER_NOT_FOUND, customerId)));

        Order order = orderRepository.findByCustomer_CustomerIdAndOrderId(customer.getCustomerId(), orderId);

        return orderMapper.mapToDto(order);
    }

    /* UPDATE ORDER STATUS */
    @Transactional
	public OrderResponse updateOrderStatus(int orderId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new AppAuthException(String.format(ORDER_NOT_FOUND, orderId)));
        
        if (order.getStatus().equalsIgnoreCase("pending")) 
        	order.setStatus("processed");        	
        else if (order.getStatus().equalsIgnoreCase("processed")) 
        	order.setStatus("delivered");
        else if (order.getStatus().equalsIgnoreCase("delivered"))
        	return orderMapper.mapToDto(order);
        
        Order _order = orderRepository.save(order);
		return orderMapper.mapToDto(_order);
	}
}
