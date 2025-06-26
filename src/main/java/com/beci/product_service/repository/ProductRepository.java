package com.beci.product_service.repository;

import com.beci.product_service.model.Category;
import com.beci.product_service.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// ProductRepository
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Page<Product> findByActiveTrue(Pageable pageable);

    List<Product> findByFeaturedTrue();

    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("{'description': {$regex: ?0, $options: 'i'}}")
    List<Product> findByDescriptionContainingIgnoreCase(String description);

    @Query("{'tags': {$in: [?0]}}")
    List<Product> findByTag(String tag);


    @Query("{'price': {$gte: ?0, $lte: ?1}, 'active': true}")
    Page<Product> findByPriceBetweenAndActiveTrue(Double minPrice, Double maxPrice, Pageable pageable);


    @Query("{'category': ?0, 'active': true}")
    Page<Product> findByCategoryAndActiveTrue(Category category, Pageable pageable);

    @Query("{'stockQuantity': {$gt: 0}, 'active': true}")
    List<Product> findInStockProducts();

    List<Product> findByBrand(String brand);

    @Query("{'$or': [ " +
            "{'name': {$regex: ?0, $options: 'i'}}, " +
            "{'description': {$regex: ?0, $options: 'i'}}, " +
            "{'tags': {$in: [?0]}}" +
            "], 'active': true}")
    Page<Product> searchProducts(String searchTerm, Pageable pageable);
}


