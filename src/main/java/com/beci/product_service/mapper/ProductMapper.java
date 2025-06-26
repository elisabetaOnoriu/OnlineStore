package com.beci.product_service.mapper;

import com.beci.product_service.dto.ProductRequest;
import com.beci.product_service.dto.ProductResponse;
import com.beci.product_service.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        if (product == null) return null;

        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());

        if (product.getCategory() != null) {
            response.setCategoryId(product.getCategory().getId());
            response.setCategoryName(product.getCategory().getName());
        }

        return response;
    }

    public Product toEntity(ProductRequest request) {
        if (request == null) return null;

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        // Category se setează separat în controller
        return product;
    }

    public void updateProductFromRequest(Product product, ProductRequest request) {
        if (product == null || request == null) return;

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        // Category se setează separat în controller
    }

    public ProductRequest toRequest(Product product) {
        if (product == null) return null;

        ProductRequest request = new ProductRequest();
        request.setName(product.getName());
        request.setDescription(product.getDescription());
        request.setPrice(product.getPrice());
        if (product.getCategory() != null) {
            request.setCategoryId(product.getCategory().getId());
        }
        return request;
    }

}
