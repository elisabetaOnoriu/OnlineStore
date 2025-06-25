package com.beci.product_service.service;

import com.beci.product_service.exception.CategoryNotFoundException;
import com.beci.product_service.model.Category;
import com.beci.product_service.repository.CategoryRepository;
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
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        logger.info("Creating new category: {}", category.getName());
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    public Category updateCategory(String id, Category category) throws CategoryNotFoundException {
        logger.info("Updating category with id: {}", id);

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setImageUrl(category.getImageUrl());
        existingCategory.setActive(category.isActive());
        existingCategory.setParentCategory(category.getParentCategory());
        existingCategory.setUpdatedAt(LocalDateTime.now());

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(String id) throws CategoryNotFoundException {
        logger.info("Deleting category with id: {}", id);

        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }

        categoryRepository.deleteById(id);
    }

    public Category getCategoryById(String id) throws CategoryNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getActiveCategories() {
        return categoryRepository.findByActiveTrue();
    }

    public List<Category> getRootCategories() {
        return categoryRepository.findByParentCategoryIsNull();
    }

    public List<Category> getSubCategories(Category parentCategory) {
        return categoryRepository.findByParentCategory(parentCategory);
    }

    public Page<Category> getActiveCategories(Pageable pageable) {
        return categoryRepository.findByActiveTrue(pageable);
    }

    public List<Category> searchCategories(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }

    public boolean isCategoryNameTaken(String name) {
        return categoryRepository.existsByName(name);
    }
}
