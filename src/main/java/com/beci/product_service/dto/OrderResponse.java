package com.beci.product_service.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private String id;
    private String orderNumber;
    private String status;
    private Double totalAmount;
    private Double shippingCost;
    private Double taxAmount;
    private Double discountAmount;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentTransactionId;
    private String notes;
    private LocalDateTime orderDate;
    private LocalDateTime shippedDate;
    private LocalDateTime deliveredDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String userId;
    private AddressResponse shippingAddress;
    private AddressResponse billingAddress;
    private List<OrderItemResponse> orderItems;

    // Getteri și setteri:

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public Double getShippingCost() { return shippingCost; }
    public void setShippingCost(Double shippingCost) { this.shippingCost = shippingCost; }

    public Double getTaxAmount() { return taxAmount; }
    public void setTaxAmount(Double taxAmount) { this.taxAmount = taxAmount; }

    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getPaymentTransactionId() { return paymentTransactionId; }
    public void setPaymentTransactionId(String paymentTransactionId) { this.paymentTransactionId = paymentTransactionId; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public LocalDateTime getShippedDate() { return shippedDate; }
    public void setShippedDate(LocalDateTime shippedDate) { this.shippedDate = shippedDate; }

    public LocalDateTime getDeliveredDate() { return deliveredDate; }
    public void setDeliveredDate(LocalDateTime deliveredDate) { this.deliveredDate = deliveredDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public AddressResponse getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(AddressResponse shippingAddress) { this.shippingAddress = shippingAddress; }

    public AddressResponse getBillingAddress() { return billingAddress; }
    public void setBillingAddress(AddressResponse billingAddress) { this.billingAddress = billingAddress; }

    public List<OrderItemResponse> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemResponse> orderItems) { this.orderItems = orderItems; }
}