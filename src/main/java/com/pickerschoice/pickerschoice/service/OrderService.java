package com.pickerschoice.pickerschoice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pickerschoice.pickerschoice.dto.OrderRequest;
import com.pickerschoice.pickerschoice.dto.OrderResponse;
import com.pickerschoice.pickerschoice.exception.AppAuthException;
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

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        OrderItemRepository orderItemRepository
    ) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderItemRepository = orderItemRepository;
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

        return getOrderResponse(_order);
    }

    /* FETCH ALL ORDERS */
    @Transactional
    public List<OrderResponse> findAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders
                .stream()
                .map(order -> getOrderResponse(order))
                .collect(Collectors.toList());
    }

    /* FETCH AN ORDER BY ORDER-ID */
    @Transactional
    public OrderResponse findById(int orderId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new AppAuthException(String.format(ORDER_NOT_FOUND, orderId)));

        return getOrderResponse(order);
    }

    /* FETCH ALL ORDERS FOR A CUSTOMER USING CUSTOMER ID */
    @Transactional
    public List<OrderResponse> findAllByCustomerId(int customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new AppAuthException(String.format(CUSTOMER_NOT_FOUND, customerId)));

        List<Order> customerOrderList = orderRepository.findAllByCustomer_CustomerId(customer.getCustomerId());

        return customerOrderList
                .stream()
                .map(order -> getOrderResponse(order))
                .collect(Collectors.toList());
    }

    /* FETCH AN ORDER FOR A CUSTOMER */
    @Transactional
    public OrderResponse findByCustomerIdAndOrderId(int customerId, int orderId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new AppAuthException(String.format(CUSTOMER_NOT_FOUND, customerId)));

        Order order = orderRepository.findByCustomer_CustomerIdAndOrderId(customer.getCustomerId(), orderId);
        return getOrderResponse(order);
    }

    private OrderResponse getOrderResponse(Order order) {
        return new OrderResponse(
                order.getOrderId(), order.getCustomer().getCustomerId(), order.getCustomerName(),
                order.getPhone(), order.getAddress(), order.getOrderDate(), order.getTotal(),
                order.getDelivered(), order.getPaid()
        );
    }
}
