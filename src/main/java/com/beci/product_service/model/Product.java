package com.beci.product_service.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description;

    private String shortDescription;

    @DecimalMin(value = "0.0", message = "Price must be positive")
    private Double price;

    private Double discountPrice;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    private String sku;
    private List<String> imageUrls = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    @DBRef
    private Category category; // @ManyToOne

    private boolean active = true;
    private boolean featured = false;

    private Double weight;
    private String dimensions;
    private String brand;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
