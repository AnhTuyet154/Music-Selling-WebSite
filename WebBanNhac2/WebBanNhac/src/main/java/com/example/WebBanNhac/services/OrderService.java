package com.example.WebBanNhac.services;

import com.example.WebBanNhac.entity.Order;
import com.example.WebBanNhac.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private IOrderRepository orderRepository;

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElse(null);
    }
    public void addOrder(Order order){
        orderRepository.save(order);
    }
    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }
    public void updateOrder(Order book){
        orderRepository.save(book);
    }
}
