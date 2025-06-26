package com.beci.product_service.mapper;

import com.beci.product_service.dto.OrderItemRequest;
import com.beci.product_service.dto.OrderItemResponse;
import com.beci.product_service.model.Order;
import com.beci.product_service.model.OrderItem;
import com.beci.product_service.model.Product;

public class OrderItemMapper {

    public static OrderItem toEntity(OrderItemRequest dto, Order order, Product product) {
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(dto.getUnitPrice());
        item.setTotalPrice(dto.getQuantity() * dto.getUnitPrice());
        return item;
    }

    public static OrderItemResponse toDTO(OrderItem item) {
        OrderItemResponse dto = new OrderItemResponse();
        dto.setId(item.getId());
        dto.setProductName(item.getProduct() != null ? item.getProduct().getName() : null);
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        return dto;
    }
}
