package com.pickerschoice.pickerschoice.dto;

public class OrderItemResponse {
    private String productName;
    private int quantity;
    private Double price;

    public OrderItemResponse(String productName, int quantity, Double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItemResponse{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", Price=" + price +
                '}';
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
