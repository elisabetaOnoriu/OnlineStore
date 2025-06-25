package com.beci.product_service.controller;

import com.beci.product_service.model.Product;
import com.beci.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // 1. Afișează lista de produse
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "product/list.html.html"; // => templates/product/list.html.html.html
    }

    // 2. Formular de creare produs
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/create.html"; // => templates/product/create.html.html
    }

    // 3. Salvează produsul nou
    @PostMapping
    public String saveProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/products";
    }

    // 4. Formular de editare produs
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "product/edit"; // => templates/product/edit.html
    }

    // 5. Actualizează produsul
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable String id, @ModelAttribute Product updatedProduct) {
        Product existing = productRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(updatedProduct.getName());
            existing.setDescription(updatedProduct.getDescription());
            existing.setPrice(updatedProduct.getPrice());
            existing.setCategory(updatedProduct.getCategory());
            productRepository.save(existing);
        }
        return "redirect:/products";
    }

    // 6. Șterge produsul
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }
}
