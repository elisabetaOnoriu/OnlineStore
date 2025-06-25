package com.beci.product_service.repository;

import com.beci.product_service.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// CategoryRepository
@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findByName(String name);

    List<Category> findByActiveTrue();

    List<Category> findByParentCategoryIsNull(); // Root categories

    List<Category> findByParentCategory(Category parentCategory); // Subcategories

    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    List<Category> findByNameContainingIgnoreCase(String name);

    Page<Category> findByActiveTrue(Pageable pageable);

    boolean existsByName(String name);
}
