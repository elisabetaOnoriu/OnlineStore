package com.beci.product_service.controller;

import com.beci.product_service.dto.ReviewRequest;
import com.beci.product_service.dto.ReviewResponse;
import com.beci.product_service.mapper.ReviewMapper;
import com.beci.product_service.model.Product;
import com.beci.product_service.model.Review;
import com.beci.product_service.model.User;
import com.beci.product_service.repository.ProductRepository;
import com.beci.product_service.repository.ReviewRepository;
import com.beci.product_service.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // 1. Listare
    @GetMapping
    public String listReviews(Model model) {
        List<ReviewResponse> reviews = reviewRepository.findAll()
                .stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());
        model.addAttribute("reviews", reviews);
        return "review/list";
    }

    // 2. Formular creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("review", new ReviewRequest());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "review/create";
    }

    // 3. Salvare recenzie
    @PostMapping
    public String saveReview(@ModelAttribute("review") @Valid ReviewRequest request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);
        Product product = productRepository.findById(request.getProductId()).orElse(null);

        if (user != null && product != null) {
            Review review = ReviewMapper.toEntity(request, user, product);
            review.setCreatedAt(LocalDateTime.now());
            review.setUpdatedAt(LocalDateTime.now());
            reviewRepository.save(review);
        }

        return "redirect:/reviews";
    }

    // 4. Formular editare
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null) return "redirect:/reviews";

        ReviewRequest dto = new ReviewRequest();
        dto.setUserId(review.getUser().getId());
        dto.setProductId(review.getProduct().getId());
        dto.setRating(review.getRating());
        dto.setTitle(review.getTitle());
        dto.setComment(review.getComment());
        dto.setVerified(review.isVerified());
        dto.setApproved(review.isApproved());

        model.addAttribute("reviewId", review.getId());
        model.addAttribute("review", dto);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        return "review/edit";
    }

    // 5. Actualizare
    @PostMapping("/update/{id}")
    public String updateReview(@PathVariable String id, @ModelAttribute("review") @Valid ReviewRequest request) {
        Review existing = reviewRepository.findById(id).orElse(null);
        if (existing != null) {
            User user = userRepository.findById(request.getUserId()).orElse(null);
            Product product = productRepository.findById(request.getProductId()).orElse(null);

            if (user != null && product != null) {
                existing.setUser(user);
                existing.setProduct(product);
                existing.setRating(request.getRating());
                existing.setTitle(request.getTitle());
                existing.setComment(request.getComment());
                existing.setVerified(request.isVerified());
                existing.setApproved(request.isApproved());
                existing.setUpdatedAt(LocalDateTime.now());

                reviewRepository.save(existing);
            }
        }

        return "redirect:/reviews";
    }

    // 6. Ștergere
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable String id) {
        reviewRepository.deleteById(id);
        return "redirect:/reviews";
    }
}
