package com.beci.product_service.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    @DBRef
    private User user; // @ManyToOne

    @DBRef
    private List<OrderItem> orderItems = new ArrayList<>(); // @OneToMany

    private String orderNumber;

    @NotNull(message = "Order status is required")
    private OrderStatus status = OrderStatus.PENDING;

    private Double totalAmount;
    private Double shippingCost = 0.0;
    private Double taxAmount = 0.0;
    private Double discountAmount = 0.0;

    @DBRef
    private Address shippingAddress;

    @DBRef
    private Address billingAddress;

    private String paymentMethod;
    private String paymentStatus = "PENDING";
    private String paymentTransactionId;

    private String notes;

    private LocalDateTime orderDate = LocalDateTime.now();
    private LocalDateTime shippedDate;
    private LocalDateTime deliveredDate;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum OrderStatus {
        PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, REFUNDED
    }

    public Order(User user, List<OrderItem> orderItems) {
        this.user = user;
        this.orderItems = orderItems;
        this.orderNumber = generateOrderNumber();
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }
}
