package com.beci.product_service.mapper;

import com.beci.product_service.dto.CategoryRequest;
import com.beci.product_service.dto.CategoryResponse;
import com.beci.product_service.model.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequest dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    public static CategoryResponse toDTO(Category category) {
        CategoryResponse dto = new CategoryResponse();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
