package com.beci.product_service.mapper;

import com.beci.product_service.dto.AddressResponse;
import com.beci.product_service.dto.OrderRequest;
import com.beci.product_service.dto.OrderResponse;
import com.beci.product_service.dto.OrderItemResponse;
import com.beci.product_service.model.Address;
import com.beci.product_service.model.Order;
import com.beci.product_service.model.OrderItem;
import com.beci.product_service.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toEntity(OrderRequest dto, User user, Address shippingAddress, Address billingAddress) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(dto.getStatus());
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setNotes(dto.getNotes());
        order.setShippingAddress(shippingAddress);
        order.setBillingAddress(billingAddress);
        return order;
    }

    public static OrderResponse toDTO(Order order) {
        OrderResponse dto = new OrderResponse();

        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setStatus(String.valueOf(Order.OrderStatus.valueOf(order.getStatus().name())));
        dto.setTotalAmount(order.getTotalAmount());
        dto.setShippingCost(order.getShippingCost());
        dto.setTaxAmount(order.getTaxAmount());
        dto.setDiscountAmount(order.getDiscountAmount());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setPaymentTransactionId(order.getPaymentTransactionId());
        dto.setNotes(order.getNotes());
        dto.setOrderDate(order.getOrderDate());
        dto.setShippedDate(order.getShippedDate());
        dto.setDeliveredDate(order.getDeliveredDate());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());

        if (order.getUser() != null) {
            dto.setUserId(order.getUser().getId());
        }

        if (order.getShippingAddress() != null) {
            dto.setShippingAddress(mapAddress(order.getShippingAddress()));
        }

        if (order.getBillingAddress() != null) {
            dto.setBillingAddress(mapAddress(order.getBillingAddress()));
        }

        if (order.getOrderItems() != null) {
            List<OrderItemResponse> items = order.getOrderItems().stream()
                    .map(OrderMapper::mapOrderItem)
                    .collect(Collectors.toList());
            dto.setOrderItems(items);
        }

        return dto;
    }

    private static AddressResponse mapAddress(Address address) {
        AddressResponse dto = new AddressResponse();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        return dto;
    }

    private static OrderItemResponse mapOrderItem(OrderItem item) {
        OrderItemResponse dto = new OrderItemResponse();
        dto.setId(item.getId());
        dto.setProductName(item.getProduct() != null ? item.getProduct().getName() : null);
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        return dto;
    }
}
