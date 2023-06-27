package com.example.WebBanNhac.services;

import com.example.WebBanNhac.daos.Cart;
import com.example.WebBanNhac.daos.Item;
import com.example.WebBanNhac.entity.Order;
import com.example.WebBanNhac.repository.IOrderRepository;
import com.example.WebBanNhac.repository.ISongRepository;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CartService {
    private static final String CART_SESSION_KEY = "cart";

    private final IOrderRepository orderRepository;
    private final ISongRepository songRepository;

    public CartService(IOrderRepository iOrderRepository, IOrderRepository orderRepository, ISongRepository songRepository) {
        this.orderRepository = orderRepository;
        this.songRepository = songRepository;
    }

    public static Cart getCart(@NotNull HttpSession session) {
        return Optional.ofNullable((Cart)
                        session.getAttribute(CART_SESSION_KEY))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    session.setAttribute(CART_SESSION_KEY, cart);
                    return cart;
                });
    }
    public static void updateCart(@NotNull HttpSession session, Cart cart) {
        session.setAttribute(CART_SESSION_KEY, cart);
    }
    public void removeCart(@NotNull HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }
    public int getSumQuantity(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToInt(Item::getQuantity)
                .sum();
    }
    public double getSumPrice(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToDouble(item -> item.getPrice() *
                        item.getQuantity())
                .sum();
    }
    public void saveCart(@NotNull HttpSession session, String email) {
        var cart = getCart(session);
        if (cart.getCartItems().isEmpty()) return;

        var order = new Order();
        order.setOrderDate(new Date());
        order.setPrice(getSumPrice(session));
        order.setEmail(email); // Set the email for the order
        orderRepository.save(order);

        cart.getCartItems().forEach(item -> {
            var orderItem = new Order();
            orderItem.setId(order.getId());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSong(songRepository.findById(item.getProductId())
                    .orElseThrow());
            orderRepository.save(orderItem);
        });

        removeCart(session);
    }

}

