package com.pickerschoice.pickerschoice.repository;

import com.pickerschoice.pickerschoice.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findAllByOrder_OrderId(int orderId);
}
