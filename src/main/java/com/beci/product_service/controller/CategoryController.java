package com.beci.product_service.controller;

import com.beci.product_service.dto.CategoryRequest;
import com.beci.product_service.dto.CategoryResponse;
import com.beci.product_service.mapper.CategoryMapper;
import com.beci.product_service.model.Category;
import com.beci.product_service.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // 1. Listă categorii
    @GetMapping
    public String listCategories(Model model) {
        List<CategoryResponse> categoryDTOs = categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
        model.addAttribute("categories", categoryDTOs);
        return "category/list";
    }

    // 2. Formular creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CategoryRequest());
        return "category/create";
    }

    // 3. Salvare categorie
    @PostMapping
    public String saveCategory(@ModelAttribute("category") @Valid CategoryRequest request) {
        Category category = CategoryMapper.toEntity(request);
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    // 4. Formular editare
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return "redirect:/categories";
        }
        CategoryRequest dto = new CategoryRequest();
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        model.addAttribute("categoryId", category.getId());
        model.addAttribute("category", dto);
        return "category/edit";
    }

    // 5. Actualizare
    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable String id,
                                 @ModelAttribute("category") @Valid CategoryRequest request) {
        Category existing = categoryRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(request.getName());
            existing.setDescription(request.getDescription());
            categoryRepository.save(existing);
        }
        return "redirect:/categories";
    }

    // 6. Ștergere
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable String id) {
        categoryRepository.deleteById(id);
        return "redirect:/categories";
    }
}
