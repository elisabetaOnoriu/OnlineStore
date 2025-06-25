package com.beci.product_service.repository;

import com.beci.product_service.model.Order;
import com.beci.product_service.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUser(User user);

    Page<Order> findByUser(User user, Pageable pageable);

    List<Order> findByStatus(Order.OrderStatus status);

    Page<Order> findByStatus(Order.OrderStatus status, Pageable pageable);

    Optional<Order> findByOrderNumber(String orderNumber);

    @Query("{'user': ?0, 'status': ?1}")
    List<Order> findByUserAndStatus(User user, Order.OrderStatus status);

    @Query("{'orderDate': {$gte: ?0, $lte: ?1}}")
    List<Order> findOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    @Query("{'totalAmount': {$gte: ?0}}")
    List<Order> findOrdersAboveAmount(Double amount);

    Page<Order> findByOrderByOrderDateDesc(Pageable pageable);

    @Query(value = "{'user': ?0}", sort = "{'orderDate': -1}")
    Page<Order> findByUserOrderByOrderDateDesc(User user, Pageable pageable);

    long countByStatus(Order.OrderStatus status);

    @Query(value = "{}", count = true)
    long getTotalOrdersCount();
}
