package com.example.WebBanNhac.controller;

import com.example.WebBanNhac.entity.Order;
import com.example.WebBanNhac.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String showAllOrders(Model model){
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/list";
    }

    @GetMapping("/add")
    public String addOrderForm(Model model){
        model.addAttribute("order", new Order());
        return "order/add";
    }
    @PostMapping("/add")public String addOrder(@ModelAttribute("order") @Validated Order order, BindingResult result) {
        if (result.hasErrors()) {
            return "order/add";
        } else {
            orderService.addOrder(order);
            return "redirect:/orders";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditOrderForm(@PathVariable("id") Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order/edit";
    }

    @PostMapping("/edit")
    public String editOrder( @ModelAttribute("order") @Validated Order order, BindingResult result) {
        if (result.hasErrors()) {
            return "order/edit";
        } else {
            orderService.updateOrder(order);
            return "redirect:/orders";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id){
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

}
