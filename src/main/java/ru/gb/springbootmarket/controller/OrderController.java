package ru.gb.springbootmarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.springbootmarket.service.OrderService;

import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getOrderPage(Principal principal) {
        if (principal == null) return "order";
        return "order-next";
    }

    @GetMapping("/success")
    public String getOrderSuccessPage() {
        return "order-success";
    }

    @PostMapping
    public String createOrder(@RequestParam String address, @RequestParam String email, Model model) {
        try {
            orderService.placeOrder(address, email);
            return "redirect:/order/success";
        } catch (IllegalStateException e) {
            model.addAttribute("illegalStateException", e);
            return "order";
        }
    }

    @PostMapping("/next")
    public String createOrder(Principal principal, Model model) {
        try {
            orderService.placeOrder(principal);
            return "redirect:/order/success";
        } catch (IllegalStateException e) {
            model.addAttribute("illegalStateException", e);
            return "order-next";
        }
    }

}