package com.example.WebBanNhac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/HINH/logo.png")
    public String redirectToHomePage() {
        return "redirect:http://localhost:8080";
    }
}