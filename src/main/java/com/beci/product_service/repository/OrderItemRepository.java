package com.beci.product_service.repository;

import com.beci.product_service.model.Order;
import com.beci.product_service.model.OrderItem;
import com.beci.product_service.model.Product;
import com.beci.product_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends MongoRepository<OrderItem, String> {

    List<OrderItem> findByOrder(Order order);

    List<OrderItem> findByProduct(Product product);

    @Query("{'order': ?0}")
    List<OrderItem> findItemsByOrder(Order order);

    @Query("{'product': ?0}")
    long countByProduct(Product product);

    @Query("{'order.user': ?0}")
    List<OrderItem> findByUser(User user);

    void deleteByOrder(Order order);
}
