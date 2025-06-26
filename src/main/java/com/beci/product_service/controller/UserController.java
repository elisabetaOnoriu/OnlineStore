package com.beci.product_service.controller;

import com.beci.product_service.dto.UserRequest;
import com.beci.product_service.dto.UserResponse;
import com.beci.product_service.mapper.UserMapper;
import com.beci.product_service.model.User;
import com.beci.product_service.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    // 1. Lista utilizatori
    @GetMapping
    public String listUsers(Model model) {
        List<UserResponse> users = userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "user/list";
    }

    // 2. Formular creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserRequest());
        return "user/create";
    }

    // 3. Salvare
    @PostMapping
    public String saveUser(@ModelAttribute("user") @Valid UserRequest request) {
        User user = userMapper.toEntity(request);
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of("ROLE_USER"));
        }
        userRepository.save(user);
        return "redirect:/users";
    }

    // 4. Formular editare
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            UserRequest dto = userMapper.toRequest(user);
            model.addAttribute("user", dto);
        }
        return "user/edit";
    }

    // 5. Actualizare
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable String id, @ModelAttribute("user") @Valid UserRequest request) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing != null) {
            userMapper.updateUserFromRequest(existing, request);
            userRepository.save(existing);
        }
        return "redirect:/users";
    }

    // 6. Ștergere
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
