package com.pickerschoice.pickerschoice.dto;

import com.pickerschoice.pickerschoice.model.Order;
import com.pickerschoice.pickerschoice.model.OrderItem;

import java.util.List;

public class OrderRequest {
    private Order order;
    private List<OrderItem> orderItems;

    @Override
    public String toString() {
        return "OrderRequest{" +
                "order=" + order +
                ", orderItems=" + orderItems +
                '}';
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
