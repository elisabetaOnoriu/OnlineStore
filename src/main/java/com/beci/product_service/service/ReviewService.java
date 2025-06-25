package com.beci.product_service.service;

import com.beci.product_service.exception.ReviewNotFoundException;
import com.beci.product_service.model.Product;
import com.beci.product_service.model.Review;
import com.beci.product_service.model.User;
import com.beci.product_service.repository.ReviewRepository;
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
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        logger.info("Creating new review for product: {}", review.getProduct().getName());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public Review updateReview(String id, Review review) throws ReviewNotFoundException {
        logger.info("Updating review with id: {}", id);

        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));

        existingReview.setRating(review.getRating());
        existingReview.setTitle(review.getTitle());
        existingReview.setComment(review.getComment());
        existingReview.setApproved(review.isApproved());
        existingReview.setUpdatedAt(LocalDateTime.now());

        return reviewRepository.save(existingReview);
    }

    public void deleteReview(String id) throws ReviewNotFoundException {
        logger.info("Deleting review with id: {}", id);

        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Review not found with id: " + id);
        }

        reviewRepository.deleteById(id);
    }

    public Review getReviewById(String id) throws ReviewNotFoundException {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));
    }

    public List<Review> getReviewsByProduct(Product product) {
        return reviewRepository.findByProduct(product);
    }

    public Page<Review> getReviewsByProduct(Product product, Pageable pageable) {
        return reviewRepository.findByProduct(product, pageable);
    }

    public List<Review> getApprovedReviewsByProduct(Product product) {
        return reviewRepository.findApprovedReviewsByProduct(product);
    }

    public Page<Review> getApprovedReviewsByProduct(Product product, Pageable pageable) {
        return reviewRepository.findApprovedReviewsByProduct(product, pageable);
    }

    public List<Review> getReviewsByUser(User user) {
        return reviewRepository.findByUser(user);
    }

    public Page<Review> getReviewsByUser(User user, Pageable pageable) {
        return reviewRepository.findByUser(user, pageable);
    }

    public List<Review> getPendingReviews() {
        return reviewRepository.findPendingApprovalReviews();
    }

    public void approveReview(String reviewId) throws ReviewNotFoundException {
        Review review = getReviewById(reviewId);
        review.setApproved(true);
        review.setUpdatedAt(LocalDateTime.now());
        reviewRepository.save(review);
    }

    public boolean hasUserReviewedProduct(User user, Product product) {
        return reviewRepository.findByUserAndProduct(user, product).isPresent();
    }
}