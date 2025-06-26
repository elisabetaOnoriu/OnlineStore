package com.beci.product_service.controller;

import com.beci.product_service.dto.ProductRequest;
import com.beci.product_service.dto.ProductResponse;
import com.beci.product_service.mapper.ProductMapper;
import com.beci.product_service.model.Category;
import com.beci.product_service.model.Product;
import com.beci.product_service.repository.CategoryRepository;
import com.beci.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    // 1. Lista produse
    @GetMapping
    public String listProducts(Model model) {
        List<ProductResponse> products = productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "product/list";
    }

    // 2. Formular creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new ProductRequest());
        model.addAttribute("categories", categoryRepository.findAll());
        return "product/create";
    }

    // 3. Salvare
    @PostMapping
    public String saveProduct(@ModelAttribute ProductRequest request) {
        Product product = productMapper.toEntity(request);
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
            product.setCategory(category);
        }
        productRepository.save(product);
        return "redirect:/products";
    }

    // 4. Formular editare
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            ProductRequest productRequest = productMapper.toRequest(product);
            model.addAttribute("product", productRequest);
            model.addAttribute("categories", categoryRepository.findAll());
        }
        return "product/edit";
    }

    // 5. Actualizare
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable String id, @ModelAttribute ProductRequest updatedRequest) {
        Product existing = productRepository.findById(id).orElse(null);
        if (existing != null) {
            productMapper.updateProductFromRequest(existing, updatedRequest);
            if (updatedRequest.getCategoryId() != null) {
                Category category = categoryRepository.findById(updatedRequest.getCategoryId()).orElse(null);
                existing.setCategory(category);
            } else {
                existing.setCategory(null);
            }
            productRepository.save(existing);
        }
        return "redirect:/products";
    }

    // 6. Ștergere
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }
}
