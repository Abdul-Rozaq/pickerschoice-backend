package com.pickerschoice.pickerschoice.dto;

import java.time.LocalDate;

public class OrderResponse {
    private int orderId;
    private int customerId;
    private String customerName;
    private String phone;
    private String address;
    private LocalDate orderDate;
    private Double total;
    private String status;
    private Boolean paid;

    public OrderResponse(int orderId, int customerId, String customerName, String phone, String address,
                         LocalDate orderDate, Double total, String status, Boolean paid) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.orderDate = orderDate;
        this.total = total;
        this.status = status;
        this.paid = paid;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
}
