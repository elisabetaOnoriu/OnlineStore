package com.beci.product_service.service;

import com.beci.product_service.exception.OrderNotFoundException;
import com.beci.product_service.model.Order;
import com.beci.product_service.model.OrderItem;
import com.beci.product_service.model.User;
import com.beci.product_service.repository.OrderItemRepository;
import com.beci.product_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductService productService;

    public Order createOrder(Order order) {
        logger.info("Creating new order for user: {}", order.getUser().getUsername());

        order.setOrderDate(LocalDateTime.now());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setOrderNumber(generateOrderNumber());

        // Calculate total amount
        Double totalAmount = order.getOrderItems().stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
        order.setTotalAmount(totalAmount + order.getShippingCost() + order.getTaxAmount() - order.getDiscountAmount());

        Order savedOrder = orderRepository.save(order);

        // Save order items
        for (OrderItem item : order.getOrderItems()) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);

            // Decrease product stock
            try {
                productService.decreaseStock(item.getProduct().getId(), item.getQuantity());
            } catch (Exception e) {
                logger.error("Failed to decrease stock for product: {}", item.getProduct().getId(), e);
            }
        }

        return savedOrder;
    }

    public Order updateOrder(String id, Order order) throws OrderNotFoundException {
        logger.info("Updating order with id: {}", id);

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        existingOrder.setStatus(order.getStatus());
        existingOrder.setShippingAddress(order.getShippingAddress());
        existingOrder.setBillingAddress(order.getBillingAddress());
        existingOrder.setPaymentMethod(order.getPaymentMethod());
        existingOrder.setPaymentStatus(order.getPaymentStatus());
        existingOrder.setNotes(order.getNotes());
        existingOrder.setUpdatedAt(LocalDateTime.now());

        if (order.getStatus() == Order.OrderStatus.SHIPPED && existingOrder.getShippedDate() == null) {
            existingOrder.setShippedDate(LocalDateTime.now());
        }

        if (order.getStatus() == Order.OrderStatus.DELIVERED && existingOrder.getDeliveredDate() == null) {
            existingOrder.setDeliveredDate(LocalDateTime.now());
        }

        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(String id) throws OrderNotFoundException {
        logger.info("Deleting order with id: {}", id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        // Delete associated order items
        orderItemRepository.deleteByOrder(order);

        orderRepository.deleteById(id);
    }

    public Order getOrderById(String id) throws OrderNotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    public Order getOrderByNumber(String orderNumber) throws OrderNotFoundException {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with number: " + orderNumber));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findByOrderByOrderDateDesc(pageable);
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public Page<Order> getOrdersByUser(User user, Pageable pageable) {
        return orderRepository.findByUserOrderByOrderDateDesc(user, pageable);
    }

    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public Page<Order> getOrdersByStatus(Order.OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable);
    }

    public void updateOrderStatus(String orderId, Order.OrderStatus status) throws OrderNotFoundException {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());

        if (status == Order.OrderStatus.SHIPPED) {
            order.setShippedDate(LocalDateTime.now());
        } else if (status == Order.OrderStatus.DELIVERED) {
            order.setDeliveredDate(LocalDateTime.now());
        }

        orderRepository.save(order);
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }

    public long getTotalOrdersCount() {
        return orderRepository.getTotalOrdersCount();
    }

    public long getOrdersCountByStatus(Order.OrderStatus status) {
        return orderRepository.countByStatus(status);
    }
}
