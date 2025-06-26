package com.beci.product_service.dto;

import com.beci.product_service.model.Order.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderRequest {

    @NotBlank
    private String userId;

    private String shippingAddressId;

    private String billingAddressId;

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    private String paymentMethod;
    private String notes;

    // Getteri și setteri

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getShippingAddressId() { return shippingAddressId; }
    public void setShippingAddressId(String shippingAddressId) { this.shippingAddressId = shippingAddressId; }

    public String getBillingAddressId() { return billingAddressId; }
    public void setBillingAddressId(String billingAddressId) { this.billingAddressId = billingAddressId; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
