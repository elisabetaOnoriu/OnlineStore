package com.beci.product_service.service;

import com.beci.product_service.exception.ProductNotFoundException;
import com.beci.product_service.model.Category;
import com.beci.product_service.model.Product;
import com.beci.product_service.model.Review;
import com.beci.product_service.repository.ProductRepository;
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
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public Product createProduct(Product product) {
        logger.info("Creating new product: {}", product.getName());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) throws ProductNotFoundException {
        logger.info("Updating product with id: {}", id);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setShortDescription(product.getShortDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscountPrice(product.getDiscountPrice());
        existingProduct.setStockQuantity(product.getStockQuantity());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setImageUrls(product.getImageUrls());
        existingProduct.setTags(product.getTags());
        existingProduct.setWeight(product.getWeight());
        existingProduct.setDimensions(product.getDimensions());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String id) throws ProductNotFoundException {
        logger.info("Deleting product with id: {}", id);

        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
    }

    public Product getProductById(String id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getActiveProducts() {
        return productRepository.findByActiveTrue();
    }

    public Page<Product> getActiveProducts(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable);
    }

    public List<Product> getFeaturedProducts() {
        return productRepository.findByFeaturedTrue();
    }

    public Page<Product> searchProducts(String searchTerm, Pageable pageable) {
        return productRepository.searchProducts(searchTerm, pageable);
    }

    public Page<Product> getProductsByCategory(Category category, Pageable pageable) {
        return productRepository.findByCategoryAndActiveTrue(category, pageable);
    }

    public Page<Product> getProductsByPriceRange(Double minPrice, Double maxPrice, Pageable pageable) {
        return productRepository.findByPriceBetweenAndActiveTrue(minPrice, maxPrice, pageable);
    }

    public List<Product> getInStockProducts() {
        return productRepository.findInStockProducts();
    }

    public void updateStock(String productId, Integer quantity) throws ProductNotFoundException {
        Product product = getProductById(productId);
        product.setStockQuantity(quantity);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    public void decreaseStock(String productId, Integer quantity) throws ProductNotFoundException {
        Product product = getProductById(productId);
        if (product.getStockQuantity() >= quantity) {
            product.setStockQuantity(product.getStockQuantity() - quantity);
            product.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }
    }

    public Double getAverageRating(String productId) throws ProductNotFoundException {
        Product product = getProductById(productId);
        List<Review> reviews = reviewRepository.findApprovedReviewsByProduct(product);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    public long getReviewCount(String productId) throws ProductNotFoundException {
        Product product = getProductById(productId);
        return reviewRepository.countReviewsByProduct(product);
    }
}
