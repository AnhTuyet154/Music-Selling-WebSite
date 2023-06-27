package com.example.WebBanNhac.controller;

import com.example.WebBanNhac.daos.Cart;
import com.example.WebBanNhac.entity.Order;
import com.example.WebBanNhac.services.CartService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Properties;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @GetMapping
    public String showCart(HttpSession session,
                           @NotNull Model model) {
        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("totalPrice",
                cartService.getSumPrice(session));
        model.addAttribute("totalQuantity",
                cartService.getSumQuantity(session));
        return "song/cart";
    }
    @GetMapping("/removeFromCart/{id}")
    public String removeFromCart(HttpSession session,
                                 @PathVariable Long id) {
        var cart = cartService.getCart(session);
        cart.removeItems(id);
        return "redirect:/cart";
    }
    @GetMapping("/updateCart/{id}/{quantity}")
    public String updateCart(HttpSession session,
                             @PathVariable Long id,
                             @PathVariable int quantity) {
        var cart = cartService.getCart(session);
        cart.updateItems(Math.toIntExact(id), quantity);
        return "song/cart";
    }
    @GetMapping("/checkout")
    public String checkout() {
        return "order/checkout"; // Show the checkout form HTML page
    }

    @PostMapping("/processCheckout")
    public String processCheckout(HttpSession session, @NotNull Model model, String name, String email) {
        Cart cart = cartService.getCart(session);

        // Create a new Order object
        Order order = new Order();
        order.setEmail(email);

        cartService.saveCart(session, email);

        // Send email to the customer
        sendEmail(email,name);

        return "order/list"; // Redirect to the order list page after checkout
    }

    private void sendEmail(String email, String customerName) {
        String fromEmail = "3consoi544@gmail.com"; // Your email address
        String password = "bwzyakxgehwqamog"; // Your email password

        String toEmail = email; // Customer's email address

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Use your email provider's SMTP server
        properties.put("mail.smtp.port", "587"); // Use the appropriate port for your email provider

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Order Complete");

            // Customize the email message
            String emailContent = "Thank you for your order, " + customerName + "!";
            message.setText(emailContent);

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}