package com.pickerschoice.pickerschoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JsonIgnore
    @OneToMany(
            mappedBy = "order",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<OrderItem> orderItems;

    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "order_date")
    private LocalDate orderDate = LocalDate.now();
    @Column(name = "delivered")
    private Boolean delivered = false;
    @Column(name = "total")
    private Double total;
    @Column(name = "paid")
    private Boolean paid = false;

    public void addItems(OrderItem orderItem) {
        if (orderItems == null) orderItems = new ArrayList<>();

        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public Order() {
    }

    public Order(String customerName, String phone, String address, Double total,
                 LocalDate orderDate, Boolean delivered, Boolean paid) {
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.total = total;
        this.orderDate = orderDate;
        this.delivered = delivered;
        this.paid = paid;
    }

    public Order(int orderId, Customer customer, String customerName, String phone, String address, LocalDate orderDate,
			Boolean delivered, Double total, Boolean paid) 
    {
		this.orderId = orderId;
		this.customer = customer;
		this.customerName = customerName;
		this.phone = phone;
		this.address = address;
		this.orderDate = orderDate;
		this.delivered = delivered;
		this.total = total;
		this.paid = paid;
	}

	@Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerName='" + customerName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", orderDate=" + orderDate +
                ", delivered=" + delivered +
                ", total=" + total +
                ", paid=" + paid +
                '}';
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
