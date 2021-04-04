package com.pickerschoice.pickerschoice.repository;

import com.pickerschoice.pickerschoice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByCustomer_CustomerId(int customerId);

    Order findByCustomer_CustomerIdAndOrderId(int customerId, int OrderId);
}
