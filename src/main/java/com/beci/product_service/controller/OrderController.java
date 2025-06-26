package com.beci.product_service.controller;

import com.beci.product_service.dto.OrderRequest;
import com.beci.product_service.dto.OrderResponse;
import com.beci.product_service.mapper.OrderMapper;
import com.beci.product_service.model.Order;
import com.beci.product_service.model.User;
import com.beci.product_service.repository.OrderRepository;
import com.beci.product_service.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. Lista comenzilor
    @GetMapping
    public String listOrders(Model model) {
        List<OrderResponse> orders = orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());

        model.addAttribute("orders", orders);
        return "order/list";
    }

    // 2. Formular creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("order", new OrderRequest());
        model.addAttribute("users", userRepository.findAll());
        return "order/create";
    }

    // 3. Salvare
    @PostMapping
    public String saveOrder(@ModelAttribute("order") @Valid OrderRequest request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) return "redirect:/orders"; // sau redirect la eroare
        Order order = OrderMapper.toEntity(request, user);
        orderRepository.save(order);
        return "redirect:/orders";
    }

    // 4. Formular editare
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return "redirect:/orders";

        OrderRequest dto = new OrderRequest();
        dto.setUserId(order.getUser().getId());
        dto.setStatus(order.getStatus());

        model.addAttribute("orderId", order.getId());
        model.addAttribute("order", dto);
        model.addAttribute("users", userRepository.findAll());

        return "order/edit";
    }

    // 5. Actualizare
    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable String id, @ModelAttribute("order") @Valid OrderRequest request) {
        Order existing = orderRepository.findById(id).orElse(null);
        if (existing != null) {
            User user = userRepository.findById(request.getUserId()).orElse(null);
            if (user != null) {
                existing.setUser(user);
                existing.setStatus(request.getStatus());
                orderRepository.save(existing);
            }
        }
        return "redirect:/orders";
    }

    // 6. Ștergere
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable String id) {
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }
}
