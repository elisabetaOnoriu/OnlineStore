package com.beci.product_service.controller;

import com.beci.product_service.dto.OrderItemRequest;
import com.beci.product_service.dto.OrderItemResponse;
import com.beci.product_service.mapper.OrderItemMapper;
import com.beci.product_service.model.Order;
import com.beci.product_service.model.OrderItem;
import com.beci.product_service.model.Product;
import com.beci.product_service.repository.OrderItemRepository;
import com.beci.product_service.repository.OrderRepository;
import com.beci.product_service.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // 1. Lista articole
    @GetMapping
    public String listItems(Model model) {
        List<OrderItemResponse> items = orderItemRepository.findAll()
                .stream()
                .map(OrderItemMapper::toDTO)
                .collect(Collectors.toList());
        model.addAttribute("items", items);
        return "order_item/list";
    }

    // 2. Formular creare
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("orderItem", new OrderItemRequest());
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "order_item/create";
    }

    // 3. Salvare articol
    @PostMapping
    public String saveItem(@ModelAttribute("orderItem") @Valid OrderItemRequest request) {
        Order order = orderRepository.findById(request.getOrderId()).orElse(null);
        Product product = productRepository.findById(request.getProductId()).orElse(null);

        if (order != null && product != null) {
            OrderItem item = OrderItemMapper.toEntity(request, order, product);
            orderItemRepository.save(item);
        }

        return "redirect:/order-items";
    }

    // 4. Formular editare
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        OrderItem item = orderItemRepository.findById(id).orElse(null);
        if (item == null) return "redirect:/order-items";

        OrderItemRequest dto = new OrderItemRequest();
        dto.setOrderId(item.getOrder().getId());
        dto.setProductId(item.getProduct().getId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());

        model.addAttribute("orderItemId", item.getId());
        model.addAttribute("orderItem", dto);
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        return "order_item/edit";
    }

    // 5. Actualizare
    @PostMapping("/update/{id}")
    public String updateItem(@PathVariable String id,
                             @ModelAttribute("orderItem") @Valid OrderItemRequest request) {

        OrderItem item = orderItemRepository.findById(id).orElse(null);
        if (item != null) {
            Order order = orderRepository.findById(request.getOrderId()).orElse(null);
            Product product = productRepository.findById(request.getProductId()).orElse(null);

            if (order != null && product != null) {
                item.setOrder(order);
                item.setProduct(product);
                item.setQuantity(request.getQuantity());
                item.setUnitPrice(request.getUnitPrice());
                item.setTotalPrice(request.getQuantity() * request.getUnitPrice());
                orderItemRepository.save(item);
            }
        }

        return "redirect:/order-items";
    }

    // 6. Ștergere
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable String id) {
        orderItemRepository.deleteById(id);
        return "redirect:/order-items";
    }
}
