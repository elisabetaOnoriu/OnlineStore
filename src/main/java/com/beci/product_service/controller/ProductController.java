package com.beci.product_service.controller;

import com.beci.product_service.dto.ProductRequest;
import com.beci.product_service.dto.ProductResponse;
import com.beci.product_service.mapper.ProductMapper;
import com.beci.product_service.model.Category;
import com.beci.product_service.model.Product;
import com.beci.product_service.repository.CategoryRepository;
import com.beci.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    // 1. Lista produse cu paginare și sortare
    @GetMapping
    public String listProducts(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Product> productPage = productRepository.findByActiveTrue(pageable);
        Page<ProductResponse> responsePage = productPage.map(productMapper::toResponse);

        model.addAttribute("products", responsePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", responsePage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);
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
