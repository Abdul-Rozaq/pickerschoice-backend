package com.pickerschoice.pickerschoice.service;

import java.util.List;
//import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class OrderService {
    private static final String CUSTOMER_NOT_FOUND = "Customer %s not found";
    private static final String ORDER_NOT_FOUND = "Order %s not found";

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private OrderItemRepository orderItemRepository;
    private OrderMapper orderMapper;
    private NotificationService notificationService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        OrderItemRepository orderItemRepository,
                        OrderMapper orderMapper,
                        NotificationService notificationService
    ) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
        this.notificationService = notificationService;
    }

    /* CREATE A NEW ORDER */
    @Transactional
    public OrderResponse saveOrder(int customerId, OrderRequest request) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new AppAuthException(String.format(CUSTOMER_NOT_FOUND, customerId)));

        customer.createOrder(request.getOrder());
        Order _order = orderRepository.save(request.getOrder());

        request.getOrderItems().forEach(orderItem -> {
            _order.addItems(orderItem);
            orderItemRepository.save(orderItem);
        });
        
//        notificationService
        return orderMapper.mapToDto(_order);
    }

    /* FETCH ALL ORDERS */
    @Transactional
    public List<OrderResponse> findAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orderMapper.mapAllToDto(orders);
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
