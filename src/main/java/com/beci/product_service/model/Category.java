package com.beci.product_service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "categories")
public class Category {

    @Id
    private String id;

    @NotBlank(message = "Category name is required")
    @Indexed(unique = true)
    private String name;

    private String description;
    private String imageUrl;
    private boolean active = true;

    @DBRef
    private Category parentCategory;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
