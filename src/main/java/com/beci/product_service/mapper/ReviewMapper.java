package com.beci.product_service.mapper;

import com.beci.product_service.dto.ReviewRequest;
import com.beci.product_service.dto.ReviewResponse;
import com.beci.product_service.model.Product;
import com.beci.product_service.model.Review;
import com.beci.product_service.model.User;

public class ReviewMapper {

    public static Review toEntity(ReviewRequest dto, User user, Product product) {
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(dto.getRating());
        review.setTitle(dto.getTitle());
        review.setComment(dto.getComment());
        review.setVerified(dto.isVerified());
        review.setApproved(dto.isApproved());
        return review;
    }

    public static ReviewResponse toDTO(Review review) {
        ReviewResponse dto = new ReviewResponse();
        dto.setId(review.getId());
        dto.setUserName(review.getUser() != null ? review.getUser().getUsername() : null);
        dto.setProductName(review.getProduct() != null ? review.getProduct().getName() : null);
        dto.setRating(review.getRating());
        dto.setTitle(review.getTitle());
        dto.setComment(review.getComment());
        dto.setVerified(review.isVerified());
        dto.setApproved(review.isApproved());
        return dto;
    }
}
