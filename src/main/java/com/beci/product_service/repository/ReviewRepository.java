package com.beci.product_service.repository;

import com.beci.product_service.model.Product;
import com.beci.product_service.model.Review;
import com.beci.product_service.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByProduct(Product product);

    Page<Review> findByProduct(Product product, Pageable pageable);

    List<Review> findByUser(User user);

    Page<Review> findByUser(User user, Pageable pageable);

    @Query("{'product': ?0, 'approved': true}")
    List<Review> findApprovedReviewsByProduct(Product product);

    @Query("{'product': ?0, 'approved': true}")
    Page<Review> findApprovedReviewsByProduct(Product product, Pageable pageable);

    @Query("{'rating': ?0}")
    List<Review> findByRating(Integer rating);

    @Query("{'product': ?0, 'rating': {$gte: ?1}}")
    List<Review> findByProductAndRatingGreaterThanEqual(Product product, Integer rating);

    @Query("{'verified': true}")
    List<Review> findVerifiedReviews();

    @Query("{'approved': false}")
    List<Review> findPendingApprovalReviews();

    @Query("{'user': ?0, 'product': ?1}")
    Optional<Review> findByUserAndProduct(User user, Product product);

    @Query(value = "{'product': ?0}", count = true)
    long countReviewsByProduct(Product product);

    @Query("{'product': ?0}")
    List<Review> findReviewsForAverageRating(Product product);
}
